package io.github.jhipster.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ApiMethodResource.
 */
@Entity
@Table(name = "api_method_resource")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ApiMethodResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "restricted_input_paths")
    private String restrictedInputPaths;

    @Column(name = "restricted_output_paths")
    private String restrictedOutputPaths;

    @NotNull
    @Column(name = "mask", nullable = false)
    private String mask;

    @NotNull
    @Column(name = "enable", nullable = false)
    private Boolean enable;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "api_method_resource_method",
               joinColumns = @JoinColumn(name = "api_method_resource_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "method_id", referencedColumnName = "id"))
    private Set<ApiPermission> methods = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("apiMethodResources")
    private ApiResource apiResource;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRestrictedInputPaths() {
        return restrictedInputPaths;
    }

    public ApiMethodResource restrictedInputPaths(String restrictedInputPaths) {
        this.restrictedInputPaths = restrictedInputPaths;
        return this;
    }

    public void setRestrictedInputPaths(String restrictedInputPaths) {
        this.restrictedInputPaths = restrictedInputPaths;
    }

    public String getRestrictedOutputPaths() {
        return restrictedOutputPaths;
    }

    public ApiMethodResource restrictedOutputPaths(String restrictedOutputPaths) {
        this.restrictedOutputPaths = restrictedOutputPaths;
        return this;
    }

    public void setRestrictedOutputPaths(String restrictedOutputPaths) {
        this.restrictedOutputPaths = restrictedOutputPaths;
    }

    public String getMask() {
        return mask;
    }

    public ApiMethodResource mask(String mask) {
        this.mask = mask;
        return this;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public Boolean isEnable() {
        return enable;
    }

    public ApiMethodResource enable(Boolean enable) {
        this.enable = enable;
        return this;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Set<ApiPermission> getMethods() {
        return methods;
    }

    public ApiMethodResource methods(Set<ApiPermission> apiPermissions) {
        this.methods = apiPermissions;
        return this;
    }

    public ApiMethodResource addMethod(ApiPermission apiPermission) {
        this.methods.add(apiPermission);
        apiPermission.getApiMethodResources().add(this);
        return this;
    }

    public ApiMethodResource removeMethod(ApiPermission apiPermission) {
        this.methods.remove(apiPermission);
        apiPermission.getApiMethodResources().remove(this);
        return this;
    }

    public void setMethods(Set<ApiPermission> apiPermissions) {
        this.methods = apiPermissions;
    }

    public ApiResource getApiResource() {
        return apiResource;
    }

    public ApiMethodResource apiResource(ApiResource apiResource) {
        this.apiResource = apiResource;
        return this;
    }

    public void setApiResource(ApiResource apiResource) {
        this.apiResource = apiResource;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiMethodResource)) {
            return false;
        }
        return id != null && id.equals(((ApiMethodResource) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ApiMethodResource{" +
            "id=" + getId() +
            ", restrictedInputPaths='" + getRestrictedInputPaths() + "'" +
            ", restrictedOutputPaths='" + getRestrictedOutputPaths() + "'" +
            ", mask='" + getMask() + "'" +
            ", enable='" + isEnable() + "'" +
            "}";
    }
}
