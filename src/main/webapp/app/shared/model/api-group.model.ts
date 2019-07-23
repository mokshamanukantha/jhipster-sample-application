import { IApiPermission } from 'app/shared/model/api-permission.model';

export interface IApiGroup {
  id?: number;
  code?: string;
  description?: string;
  permissions?: IApiPermission[];
}

export class ApiGroup implements IApiGroup {
  constructor(public id?: number, public code?: string, public description?: string, public permissions?: IApiPermission[]) {}
}
