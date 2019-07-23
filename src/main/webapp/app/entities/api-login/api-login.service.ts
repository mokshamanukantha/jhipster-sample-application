import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApiLogin } from 'app/shared/model/api-login.model';

type EntityResponseType = HttpResponse<IApiLogin>;
type EntityArrayResponseType = HttpResponse<IApiLogin[]>;

@Injectable({ providedIn: 'root' })
export class ApiLoginService {
  public resourceUrl = SERVER_API_URL + 'api/api-logins';

  constructor(protected http: HttpClient) {}

  create(apiLogin: IApiLogin): Observable<EntityResponseType> {
    return this.http.post<IApiLogin>(this.resourceUrl, apiLogin, { observe: 'response' });
  }

  update(apiLogin: IApiLogin): Observable<EntityResponseType> {
    return this.http.put<IApiLogin>(this.resourceUrl, apiLogin, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IApiLogin>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IApiLogin[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
