import { IApiService } from 'app/shared/model/api-service.model';
import { IApiMethodResource } from 'app/shared/model/api-method-resource.model';
import { IApiPermission } from 'app/shared/model/api-permission.model';

export const enum MethodType {
  URL = 'URL',
  COMPONENT = 'COMPONENT',
  OBJECT = 'OBJECT'
}

export const enum HttpMethod {
  GET = 'GET',
  POST = 'POST',
  PUT = 'PUT',
  DELETE = 'DELETE',
  PATCH = 'PATCH',
  COPY = 'COPY',
  HEAD = 'HEAD',
  OPTIONS = 'OPTIONS',
  LINK = 'LINK',
  UNLINK = 'UNLINK',
  PURGE = 'PURGE',
  LOCK = 'LOCK',
  UNLOCK = 'UNLOCK',
  PROPFIND = 'PROPFIND',
  VIEW = 'VIEW'
}

export interface IApiServiceMethod {
  id?: number;
  type?: MethodType;
  version?: string;
  path?: string;
  method?: HttpMethod;
  description?: string;
  apiService?: IApiService;
  objects?: IApiMethodResource[];
  methods?: IApiPermission[];
}

export class ApiServiceMethod implements IApiServiceMethod {
  constructor(
    public id?: number,
    public type?: MethodType,
    public version?: string,
    public path?: string,
    public method?: HttpMethod,
    public description?: string,
    public apiService?: IApiService,
    public objects?: IApiMethodResource[],
    public methods?: IApiPermission[]
  ) {}
}
