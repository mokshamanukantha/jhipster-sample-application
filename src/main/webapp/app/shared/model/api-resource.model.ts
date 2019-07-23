import { IApiMethodResource } from 'app/shared/model/api-method-resource.model';

export interface IApiResource {
  id?: number;
  name?: string;
  description?: string;
  resources?: IApiMethodResource[];
}

export class ApiResource implements IApiResource {
  constructor(public id?: number, public name?: string, public description?: string, public resources?: IApiMethodResource[]) {}
}
