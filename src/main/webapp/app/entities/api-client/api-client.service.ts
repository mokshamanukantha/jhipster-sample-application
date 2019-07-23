import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApiClient } from 'app/shared/model/api-client.model';

type EntityResponseType = HttpResponse<IApiClient>;
type EntityArrayResponseType = HttpResponse<IApiClient[]>;

@Injectable({ providedIn: 'root' })
export class ApiClientService {
  public resourceUrl = SERVER_API_URL + 'api/api-clients';

  constructor(protected http: HttpClient) {}

  create(apiClient: IApiClient): Observable<EntityResponseType> {
    return this.http.post<IApiClient>(this.resourceUrl, apiClient, { observe: 'response' });
  }

  update(apiClient: IApiClient): Observable<EntityResponseType> {
    return this.http.put<IApiClient>(this.resourceUrl, apiClient, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IApiClient>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IApiClient[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
