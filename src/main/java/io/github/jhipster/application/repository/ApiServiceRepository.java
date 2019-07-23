package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ApiService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ApiService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApiServiceRepository extends JpaRepository<ApiService, Long> {

}
