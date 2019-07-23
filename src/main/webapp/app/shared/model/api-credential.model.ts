export interface IApiCredential {
  id?: number;
  name?: string;
  description?: string;
  idpName?: string;
  idpDscription?: string;
  entityId?: string;
  clientId?: string;
  clientSecret?: string;
  issuer?: string;
  scope?: string;
  callbackUrl?: string;
  certCacheTTL?: number;
  tokenTTTL?: number;
  idpUrl?: string;
  idpValidateUrl?: string;
  idpUserInfoUrl?: string;
  idpLogoutUrl?: string;
}

export class ApiCredential implements IApiCredential {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public idpName?: string,
    public idpDscription?: string,
    public entityId?: string,
    public clientId?: string,
    public clientSecret?: string,
    public issuer?: string,
    public scope?: string,
    public callbackUrl?: string,
    public certCacheTTL?: number,
    public tokenTTTL?: number,
    public idpUrl?: string,
    public idpValidateUrl?: string,
    public idpUserInfoUrl?: string,
    public idpLogoutUrl?: string
  ) {}
}
