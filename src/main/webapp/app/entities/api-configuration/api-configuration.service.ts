import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApiConfiguration } from 'app/shared/model/api-configuration.model';

type EntityResponseType = HttpResponse<IApiConfiguration>;
type EntityArrayResponseType = HttpResponse<IApiConfiguration[]>;

@Injectable({ providedIn: 'root' })
export class ApiConfigurationService {
  public resourceUrl = SERVER_API_URL + 'api/api-configurations';

  constructor(protected http: HttpClient) {}

  create(apiConfiguration: IApiConfiguration): Observable<EntityResponseType> {
    return this.http.post<IApiConfiguration>(this.resourceUrl, apiConfiguration, { observe: 'response' });
  }

  update(apiConfiguration: IApiConfiguration): Observable<EntityResponseType> {
    return this.http.put<IApiConfiguration>(this.resourceUrl, apiConfiguration, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IApiConfiguration>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IApiConfiguration[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
