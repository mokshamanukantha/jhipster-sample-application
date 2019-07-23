import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApiCredential } from 'app/shared/model/api-credential.model';

type EntityResponseType = HttpResponse<IApiCredential>;
type EntityArrayResponseType = HttpResponse<IApiCredential[]>;

@Injectable({ providedIn: 'root' })
export class ApiCredentialService {
  public resourceUrl = SERVER_API_URL + 'api/api-credentials';

  constructor(protected http: HttpClient) {}

  create(apiCredential: IApiCredential): Observable<EntityResponseType> {
    return this.http.post<IApiCredential>(this.resourceUrl, apiCredential, { observe: 'response' });
  }

  update(apiCredential: IApiCredential): Observable<EntityResponseType> {
    return this.http.put<IApiCredential>(this.resourceUrl, apiCredential, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IApiCredential>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IApiCredential[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
