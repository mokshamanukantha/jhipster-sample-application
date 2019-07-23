import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApiMethodResource } from 'app/shared/model/api-method-resource.model';

type EntityResponseType = HttpResponse<IApiMethodResource>;
type EntityArrayResponseType = HttpResponse<IApiMethodResource[]>;

@Injectable({ providedIn: 'root' })
export class ApiMethodResourceService {
  public resourceUrl = SERVER_API_URL + 'api/api-method-resources';

  constructor(protected http: HttpClient) {}

  create(apiMethodResource: IApiMethodResource): Observable<EntityResponseType> {
    return this.http.post<IApiMethodResource>(this.resourceUrl, apiMethodResource, { observe: 'response' });
  }

  update(apiMethodResource: IApiMethodResource): Observable<EntityResponseType> {
    return this.http.put<IApiMethodResource>(this.resourceUrl, apiMethodResource, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IApiMethodResource>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IApiMethodResource[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
