package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ApiClient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ApiClient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApiClientRepository extends JpaRepository<ApiClient, Long> {

}
