import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApiServiceMethod } from 'app/shared/model/api-service-method.model';

type EntityResponseType = HttpResponse<IApiServiceMethod>;
type EntityArrayResponseType = HttpResponse<IApiServiceMethod[]>;

@Injectable({ providedIn: 'root' })
export class ApiServiceMethodService {
  public resourceUrl = SERVER_API_URL + 'api/api-service-methods';

  constructor(protected http: HttpClient) {}

  create(apiServiceMethod: IApiServiceMethod): Observable<EntityResponseType> {
    return this.http.post<IApiServiceMethod>(this.resourceUrl, apiServiceMethod, { observe: 'response' });
  }

  update(apiServiceMethod: IApiServiceMethod): Observable<EntityResponseType> {
    return this.http.put<IApiServiceMethod>(this.resourceUrl, apiServiceMethod, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IApiServiceMethod>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IApiServiceMethod[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
