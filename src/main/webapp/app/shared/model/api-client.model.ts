import { IApiLogin } from 'app/shared/model/api-login.model';

export interface IApiClient {
  id?: number;
  clientId?: string;
  name?: string;
  description?: string;
  cookieEnable?: boolean;
  cookieTTL?: number;
  clientCallback?: string;
  credentials?: IApiLogin[];
}

export class ApiClient implements IApiClient {
  constructor(
    public id?: number,
    public clientId?: string,
    public name?: string,
    public description?: string,
    public cookieEnable?: boolean,
    public cookieTTL?: number,
    public clientCallback?: string,
    public credentials?: IApiLogin[]
  ) {
    this.cookieEnable = this.cookieEnable || false;
  }
}
