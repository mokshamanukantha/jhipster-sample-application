export interface IApiConfiguration {
  id?: number;
  code?: string;
  description?: string;
  value?: string;
}

export class ApiConfiguration implements IApiConfiguration {
  constructor(public id?: number, public code?: string, public description?: string, public value?: string) {}
}
