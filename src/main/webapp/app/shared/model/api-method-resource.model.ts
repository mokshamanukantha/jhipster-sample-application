import { IApiPermission } from 'app/shared/model/api-permission.model';
import { IApiResource } from 'app/shared/model/api-resource.model';

export interface IApiMethodResource {
  id?: number;
  restrictedInputPaths?: string;
  restrictedOutputPaths?: string;
  mask?: string;
  enable?: boolean;
  methods?: IApiPermission[];
  apiResource?: IApiResource;
}

export class ApiMethodResource implements IApiMethodResource {
  constructor(
    public id?: number,
    public restrictedInputPaths?: string,
    public restrictedOutputPaths?: string,
    public mask?: string,
    public enable?: boolean,
    public methods?: IApiPermission[],
    public apiResource?: IApiResource
  ) {
    this.enable = this.enable || false;
  }
}
