import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApiSession } from 'app/shared/model/api-session.model';

type EntityResponseType = HttpResponse<IApiSession>;
type EntityArrayResponseType = HttpResponse<IApiSession[]>;

@Injectable({ providedIn: 'root' })
export class ApiSessionService {
  public resourceUrl = SERVER_API_URL + 'api/api-sessions';

  constructor(protected http: HttpClient) {}

  create(apiSession: IApiSession): Observable<EntityResponseType> {
    return this.http.post<IApiSession>(this.resourceUrl, apiSession, { observe: 'response' });
  }

  update(apiSession: IApiSession): Observable<EntityResponseType> {
    return this.http.put<IApiSession>(this.resourceUrl, apiSession, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IApiSession>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IApiSession[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
