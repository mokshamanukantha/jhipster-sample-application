package io.github.jhipster.application.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import io.github.jhipster.application.domain.enumeration.AuthState;

/**
 * A ApiService.
 */
@Entity
@Table(name = "api_service")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ApiService implements Serializable {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "authentication_state")
    private AuthState authenticationState;

    @Enumerated(EnumType.STRING)
    @Column(name = "authorization_state")
    private AuthState authorizationState;

    @OneToMany(mappedBy = "apiService")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ApiServiceMethod> modules = new HashSet<>();

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

    public ApiService name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ApiService description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AuthState getAuthenticationState() {
        return authenticationState;
    }

    public ApiService authenticationState(AuthState authenticationState) {
        this.authenticationState = authenticationState;
        return this;
    }

    public void setAuthenticationState(AuthState authenticationState) {
        this.authenticationState = authenticationState;
    }

    public AuthState getAuthorizationState() {
        return authorizationState;
    }

    public ApiService authorizationState(AuthState authorizationState) {
        this.authorizationState = authorizationState;
        return this;
    }

    public void setAuthorizationState(AuthState authorizationState) {
        this.authorizationState = authorizationState;
    }

    public Set<ApiServiceMethod> getModules() {
        return modules;
    }

    public ApiService modules(Set<ApiServiceMethod> apiServiceMethods) {
        this.modules = apiServiceMethods;
        return this;
    }

    public ApiService addModule(ApiServiceMethod apiServiceMethod) {
        this.modules.add(apiServiceMethod);
        apiServiceMethod.setApiService(this);
        return this;
    }

    public ApiService removeModule(ApiServiceMethod apiServiceMethod) {
        this.modules.remove(apiServiceMethod);
        apiServiceMethod.setApiService(null);
        return this;
    }

    public void setModules(Set<ApiServiceMethod> apiServiceMethods) {
        this.modules = apiServiceMethods;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiService)) {
            return false;
        }
        return id != null && id.equals(((ApiService) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ApiService{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", authenticationState='" + getAuthenticationState() + "'" +
            ", authorizationState='" + getAuthorizationState() + "'" +
            "}";
    }
}
