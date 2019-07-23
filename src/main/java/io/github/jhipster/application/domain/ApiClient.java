package io.github.jhipster.application.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ApiClient.
 */
@Entity
@Table(name = "api_client")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ApiClient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id")
    private String clientId;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "cookie_enable", nullable = false)
    private Boolean cookieEnable;

    @NotNull
    @Column(name = "cookie_ttl", nullable = false)
    private Long cookieTTL;

    @NotNull
    @Column(name = "client_callback", nullable = false)
    private String clientCallback;

    @OneToMany(mappedBy = "apiClient")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ApiLogin> credentials = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public ApiClient clientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public ApiClient name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ApiClient description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isCookieEnable() {
        return cookieEnable;
    }

    public ApiClient cookieEnable(Boolean cookieEnable) {
        this.cookieEnable = cookieEnable;
        return this;
    }

    public void setCookieEnable(Boolean cookieEnable) {
        this.cookieEnable = cookieEnable;
    }

    public Long getCookieTTL() {
        return cookieTTL;
    }

    public ApiClient cookieTTL(Long cookieTTL) {
        this.cookieTTL = cookieTTL;
        return this;
    }

    public void setCookieTTL(Long cookieTTL) {
        this.cookieTTL = cookieTTL;
    }

    public String getClientCallback() {
        return clientCallback;
    }

    public ApiClient clientCallback(String clientCallback) {
        this.clientCallback = clientCallback;
        return this;
    }

    public void setClientCallback(String clientCallback) {
        this.clientCallback = clientCallback;
    }

    public Set<ApiLogin> getCredentials() {
        return credentials;
    }

    public ApiClient credentials(Set<ApiLogin> apiLogins) {
        this.credentials = apiLogins;
        return this;
    }

    public ApiClient addCredential(ApiLogin apiLogin) {
        this.credentials.add(apiLogin);
        apiLogin.setApiClient(this);
        return this;
    }

    public ApiClient removeCredential(ApiLogin apiLogin) {
        this.credentials.remove(apiLogin);
        apiLogin.setApiClient(null);
        return this;
    }

    public void setCredentials(Set<ApiLogin> apiLogins) {
        this.credentials = apiLogins;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiClient)) {
            return false;
        }
        return id != null && id.equals(((ApiClient) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ApiClient{" +
            "id=" + getId() +
            ", clientId='" + getClientId() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", cookieEnable='" + isCookieEnable() + "'" +
            ", cookieTTL=" + getCookieTTL() +
            ", clientCallback='" + getClientCallback() + "'" +
            "}";
    }
}
