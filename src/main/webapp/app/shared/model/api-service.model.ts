import { IApiServiceMethod } from 'app/shared/model/api-service-method.model';

export const enum AuthState {
  ENABLED = 'ENABLED',
  DISABLED = 'DISABLED'
}

export interface IApiService {
  id?: number;
  name?: string;
  description?: string;
  authenticationState?: AuthState;
  authorizationState?: AuthState;
  modules?: IApiServiceMethod[];
}

export class ApiService implements IApiService {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public authenticationState?: AuthState,
    public authorizationState?: AuthState,
    public modules?: IApiServiceMethod[]
  ) {}
}
