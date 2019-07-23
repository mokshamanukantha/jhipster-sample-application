import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApiService } from 'app/shared/model/api-service.model';

type EntityResponseType = HttpResponse<IApiService>;
type EntityArrayResponseType = HttpResponse<IApiService[]>;

@Injectable({ providedIn: 'root' })
export class ApiServiceService {
  public resourceUrl = SERVER_API_URL + 'api/api-services';

  constructor(protected http: HttpClient) {}

  create(apiService: IApiService): Observable<EntityResponseType> {
    return this.http.post<IApiService>(this.resourceUrl, apiService, { observe: 'response' });
  }

  update(apiService: IApiService): Observable<EntityResponseType> {
    return this.http.put<IApiService>(this.resourceUrl, apiService, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IApiService>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IApiService[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
