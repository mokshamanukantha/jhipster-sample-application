package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ApiCredential;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ApiCredential entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApiCredentialRepository extends JpaRepository<ApiCredential, Long> {

}
