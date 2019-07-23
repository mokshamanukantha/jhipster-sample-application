export interface IApiSession {
  id?: number;
  sessionId?: string;
  token?: string;
}

export class ApiSession implements IApiSession {
  constructor(public id?: number, public sessionId?: string, public token?: string) {}
}
