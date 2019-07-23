package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ApiResource;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ApiResource entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApiResourceRepository extends JpaRepository<ApiResource, Long> {

}
