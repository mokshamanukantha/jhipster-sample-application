import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApiPermission } from 'app/shared/model/api-permission.model';

type EntityResponseType = HttpResponse<IApiPermission>;
type EntityArrayResponseType = HttpResponse<IApiPermission[]>;

@Injectable({ providedIn: 'root' })
export class ApiPermissionService {
  public resourceUrl = SERVER_API_URL + 'api/api-permissions';

  constructor(protected http: HttpClient) {}

  create(apiPermission: IApiPermission): Observable<EntityResponseType> {
    return this.http.post<IApiPermission>(this.resourceUrl, apiPermission, { observe: 'response' });
  }

  update(apiPermission: IApiPermission): Observable<EntityResponseType> {
    return this.http.put<IApiPermission>(this.resourceUrl, apiPermission, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IApiPermission>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IApiPermission[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
