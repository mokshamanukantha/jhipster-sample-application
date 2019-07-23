package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ApiPermission;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ApiPermission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApiPermissionRepository extends JpaRepository<ApiPermission, Long> {

}
