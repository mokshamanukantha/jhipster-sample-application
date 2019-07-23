import { IApiClient } from 'app/shared/model/api-client.model';
import { IApiCredential } from 'app/shared/model/api-credential.model';

export interface IApiLogin {
  id?: number;
  name?: string;
  description?: string;
  apiClient?: IApiClient;
  type?: IApiCredential;
}

export class ApiLogin implements IApiLogin {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public apiClient?: IApiClient,
    public type?: IApiCredential
  ) {}
}
