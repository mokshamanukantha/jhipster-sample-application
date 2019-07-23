package io.github.jhipster.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import io.github.jhipster.application.domain.enumeration.MethodType;

import io.github.jhipster.application.domain.enumeration.HttpMethod;

/**
 * A ApiServiceMethod.
 */
@Entity
@Table(name = "api_service_method")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ApiServiceMethod implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private MethodType type;

    @NotNull
    @Column(name = "version", nullable = false)
    private String version;

    @NotNull
    @Column(name = "path", nullable = false)
    private String path;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "method", nullable = false)
    private HttpMethod method;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("apiServiceMethods")
    private ApiService apiService;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "api_service_method_object",
               joinColumns = @JoinColumn(name = "api_service_method_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "object_id", referencedColumnName = "id"))
    private Set<ApiMethodResource> objects = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "api_service_method_method",
               joinColumns = @JoinColumn(name = "api_service_method_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "method_id", referencedColumnName = "id"))
    private Set<ApiPermission> methods = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MethodType getType() {
        return type;
    }

    public ApiServiceMethod type(MethodType type) {
        this.type = type;
        return this;
    }

    public void setType(MethodType type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public ApiServiceMethod version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPath() {
        return path;
    }

    public ApiServiceMethod path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public ApiServiceMethod method(HttpMethod method) {
        this.method = method;
        return this;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getDescription() {
        return description;
    }

    public ApiServiceMethod description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ApiService getApiService() {
        return apiService;
    }

    public ApiServiceMethod apiService(ApiService apiService) {
        this.apiService = apiService;
        return this;
    }

    public void setApiService(ApiService apiService) {
        this.apiService = apiService;
    }

    public Set<ApiMethodResource> getObjects() {
        return objects;
    }

    public ApiServiceMethod objects(Set<ApiMethodResource> apiMethodResources) {
        this.objects = apiMethodResources;
        return this;
    }

    public ApiServiceMethod addObject(ApiMethodResource apiMethodResource) {
        this.objects.add(apiMethodResource);
        apiMethodResource.getApiServiceMethods().add(this);
        return this;
    }

    public ApiServiceMethod removeObject(ApiMethodResource apiMethodResource) {
        this.objects.remove(apiMethodResource);
        apiMethodResource.getApiServiceMethods().remove(this);
        return this;
    }

    public void setObjects(Set<ApiMethodResource> apiMethodResources) {
        this.objects = apiMethodResources;
    }

    public Set<ApiPermission> getMethods() {
        return methods;
    }

    public ApiServiceMethod methods(Set<ApiPermission> apiPermissions) {
        this.methods = apiPermissions;
        return this;
    }

    public ApiServiceMethod addMethod(ApiPermission apiPermission) {
        this.methods.add(apiPermission);
        apiPermission.getApiServiceMethods().add(this);
        return this;
    }

    public ApiServiceMethod removeMethod(ApiPermission apiPermission) {
        this.methods.remove(apiPermission);
        apiPermission.getApiServiceMethods().remove(this);
        return this;
    }

    public void setMethods(Set<ApiPermission> apiPermissions) {
        this.methods = apiPermissions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiServiceMethod)) {
            return false;
        }
        return id != null && id.equals(((ApiServiceMethod) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ApiServiceMethod{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", version='" + getVersion() + "'" +
            ", path='" + getPath() + "'" +
            ", method='" + getMethod() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
