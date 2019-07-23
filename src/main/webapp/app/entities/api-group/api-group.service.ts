import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApiGroup } from 'app/shared/model/api-group.model';

type EntityResponseType = HttpResponse<IApiGroup>;
type EntityArrayResponseType = HttpResponse<IApiGroup[]>;

@Injectable({ providedIn: 'root' })
export class ApiGroupService {
  public resourceUrl = SERVER_API_URL + 'api/api-groups';

  constructor(protected http: HttpClient) {}

  create(apiGroup: IApiGroup): Observable<EntityResponseType> {
    return this.http.post<IApiGroup>(this.resourceUrl, apiGroup, { observe: 'response' });
  }

  update(apiGroup: IApiGroup): Observable<EntityResponseType> {
    return this.http.put<IApiGroup>(this.resourceUrl, apiGroup, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IApiGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IApiGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
