package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ApiLogin;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ApiLogin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApiLoginRepository extends JpaRepository<ApiLogin, Long> {

}
