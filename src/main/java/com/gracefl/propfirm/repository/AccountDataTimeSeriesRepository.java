package com.gracefl.propfirm.repository;

import com.gracefl.propfirm.domain.AccountDataTimeSeries;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AccountDataTimeSeries entity.
 */
@Repository
public interface AccountDataTimeSeriesRepository extends JpaRepository<AccountDataTimeSeries, Long> {
    default Optional<AccountDataTimeSeries> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AccountDataTimeSeries> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AccountDataTimeSeries> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct accountDataTimeSeries from AccountDataTimeSeries accountDataTimeSeries left join fetch accountDataTimeSeries.mt4Account",
        countQuery = "select count(distinct accountDataTimeSeries) from AccountDataTimeSeries accountDataTimeSeries"
    )
    Page<AccountDataTimeSeries> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct accountDataTimeSeries from AccountDataTimeSeries accountDataTimeSeries left join fetch accountDataTimeSeries.mt4Account"
    )
    List<AccountDataTimeSeries> findAllWithToOneRelationships();

    @Query(
        "select accountDataTimeSeries from AccountDataTimeSeries accountDataTimeSeries left join fetch accountDataTimeSeries.mt4Account where accountDataTimeSeries.id =:id"
    )
    Optional<AccountDataTimeSeries> findOneWithToOneRelationships(@Param("id") Long id);
}
