package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ApiServiceMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ApiServiceMethod entity.
 */
@Repository
public interface ApiServiceMethodRepository extends JpaRepository<ApiServiceMethod, Long> {

    @Query(value = "select distinct apiServiceMethod from ApiServiceMethod apiServiceMethod left join fetch apiServiceMethod.objects left join fetch apiServiceMethod.methods",
        countQuery = "select count(distinct apiServiceMethod) from ApiServiceMethod apiServiceMethod")
    Page<ApiServiceMethod> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct apiServiceMethod from ApiServiceMethod apiServiceMethod left join fetch apiServiceMethod.objects left join fetch apiServiceMethod.methods")
    List<ApiServiceMethod> findAllWithEagerRelationships();

    @Query("select apiServiceMethod from ApiServiceMethod apiServiceMethod left join fetch apiServiceMethod.objects left join fetch apiServiceMethod.methods where apiServiceMethod.id =:id")
    Optional<ApiServiceMethod> findOneWithEagerRelationships(@Param("id") Long id);

}
