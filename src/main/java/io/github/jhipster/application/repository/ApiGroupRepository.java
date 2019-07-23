package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ApiGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ApiGroup entity.
 */
@Repository
public interface ApiGroupRepository extends JpaRepository<ApiGroup, Long> {

    @Query(value = "select distinct apiGroup from ApiGroup apiGroup left join fetch apiGroup.permissions",
        countQuery = "select count(distinct apiGroup) from ApiGroup apiGroup")
    Page<ApiGroup> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct apiGroup from ApiGroup apiGroup left join fetch apiGroup.permissions")
    List<ApiGroup> findAllWithEagerRelationships();

    @Query("select apiGroup from ApiGroup apiGroup left join fetch apiGroup.permissions where apiGroup.id =:id")
    Optional<ApiGroup> findOneWithEagerRelationships(@Param("id") Long id);

}
