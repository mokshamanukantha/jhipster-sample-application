export interface IApiPermission {
  id?: number;
  code?: string;
  description?: string;
}

export class ApiPermission implements IApiPermission {
  constructor(public id?: number, public code?: string, public description?: string) {}
}
