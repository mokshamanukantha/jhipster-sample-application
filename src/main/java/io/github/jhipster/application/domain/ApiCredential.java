package io.github.jhipster.application.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ApiCredential.
 */
@Entity
@Table(name = "api_credential")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ApiCredential implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "idp_name", nullable = false)
    private String idpName;

    @NotNull
    @Column(name = "idp_dscription", nullable = false)
    private String idpDscription;

    @NotNull
    @Column(name = "entity_id", nullable = false)
    private String entityId;

    @NotNull
    @Column(name = "client_id", nullable = false)
    private String clientId;

    @NotNull
    @Column(name = "client_secret", nullable = false)
    private String clientSecret;

    @NotNull
    @Column(name = "issuer", nullable = false)
    private String issuer;

    @NotNull
    @Column(name = "scope", nullable = false)
    private String scope;

    @NotNull
    @Column(name = "callback_url", nullable = false)
    private String callbackUrl;

    @NotNull
    @Column(name = "cert_cache_ttl", nullable = false)
    private Long certCacheTTL;

    @NotNull
    @Column(name = "token_tttl", nullable = false)
    private Long tokenTTTL;

    @NotNull
    @Column(name = "idp_url", nullable = false)
    private String idpUrl;

    @NotNull
    @Column(name = "idp_validate_url", nullable = false)
    private String idpValidateUrl;

    @NotNull
    @Column(name = "idp_user_info_url", nullable = false)
    private String idpUserInfoUrl;

    @NotNull
    @Column(name = "idp_logout_url", nullable = false)
    private String idpLogoutUrl;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ApiCredential name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ApiCredential description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdpName() {
        return idpName;
    }

    public ApiCredential idpName(String idpName) {
        this.idpName = idpName;
        return this;
    }

    public void setIdpName(String idpName) {
        this.idpName = idpName;
    }

    public String getIdpDscription() {
        return idpDscription;
    }

    public ApiCredential idpDscription(String idpDscription) {
        this.idpDscription = idpDscription;
        return this;
    }

    public void setIdpDscription(String idpDscription) {
        this.idpDscription = idpDscription;
    }

    public String getEntityId() {
        return entityId;
    }

    public ApiCredential entityId(String entityId) {
        this.entityId = entityId;
        return this;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getClientId() {
        return clientId;
    }

    public ApiCredential clientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public ApiCredential clientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getIssuer() {
        return issuer;
    }

    public ApiCredential issuer(String issuer) {
        this.issuer = issuer;
        return this;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getScope() {
        return scope;
    }

    public ApiCredential scope(String scope) {
        this.scope = scope;
        return this;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public ApiCredential callbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
        return this;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public Long getCertCacheTTL() {
        return certCacheTTL;
    }

    public ApiCredential certCacheTTL(Long certCacheTTL) {
        this.certCacheTTL = certCacheTTL;
        return this;
    }

    public void setCertCacheTTL(Long certCacheTTL) {
        this.certCacheTTL = certCacheTTL;
    }

    public Long getTokenTTTL() {
        return tokenTTTL;
    }

    public ApiCredential tokenTTTL(Long tokenTTTL) {
        this.tokenTTTL = tokenTTTL;
        return this;
    }

    public void setTokenTTTL(Long tokenTTTL) {
        this.tokenTTTL = tokenTTTL;
    }

    public String getIdpUrl() {
        return idpUrl;
    }

    public ApiCredential idpUrl(String idpUrl) {
        this.idpUrl = idpUrl;
        return this;
    }

    public void setIdpUrl(String idpUrl) {
        this.idpUrl = idpUrl;
    }

    public String getIdpValidateUrl() {
        return idpValidateUrl;
    }

    public ApiCredential idpValidateUrl(String idpValidateUrl) {
        this.idpValidateUrl = idpValidateUrl;
        return this;
    }

    public void setIdpValidateUrl(String idpValidateUrl) {
        this.idpValidateUrl = idpValidateUrl;
    }

    public String getIdpUserInfoUrl() {
        return idpUserInfoUrl;
    }

    public ApiCredential idpUserInfoUrl(String idpUserInfoUrl) {
        this.idpUserInfoUrl = idpUserInfoUrl;
        return this;
    }

    public void setIdpUserInfoUrl(String idpUserInfoUrl) {
        this.idpUserInfoUrl = idpUserInfoUrl;
    }

    public String getIdpLogoutUrl() {
        return idpLogoutUrl;
    }

    public ApiCredential idpLogoutUrl(String idpLogoutUrl) {
        this.idpLogoutUrl = idpLogoutUrl;
        return this;
    }

    public void setIdpLogoutUrl(String idpLogoutUrl) {
        this.idpLogoutUrl = idpLogoutUrl;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiCredential)) {
            return false;
        }
        return id != null && id.equals(((ApiCredential) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ApiCredential{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", idpName='" + getIdpName() + "'" +
            ", idpDscription='" + getIdpDscription() + "'" +
            ", entityId='" + getEntityId() + "'" +
            ", clientId='" + getClientId() + "'" +
            ", clientSecret='" + getClientSecret() + "'" +
            ", issuer='" + getIssuer() + "'" +
            ", scope='" + getScope() + "'" +
            ", callbackUrl='" + getCallbackUrl() + "'" +
            ", certCacheTTL=" + getCertCacheTTL() +
            ", tokenTTTL=" + getTokenTTTL() +
            ", idpUrl='" + getIdpUrl() + "'" +
            ", idpValidateUrl='" + getIdpValidateUrl() + "'" +
            ", idpUserInfoUrl='" + getIdpUserInfoUrl() + "'" +
            ", idpLogoutUrl='" + getIdpLogoutUrl() + "'" +
            "}";
    }
}
