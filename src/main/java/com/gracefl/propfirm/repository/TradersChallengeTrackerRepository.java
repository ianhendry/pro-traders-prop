package com.gracefl.propfirm.repository;

import com.gracefl.propfirm.domain.TradersChallengeTracker;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TradersChallengeTracker entity.
 */
@Repository
public interface TradersChallengeTrackerRepository
    extends JpaRepository<TradersChallengeTracker, Long>, JpaSpecificationExecutor<TradersChallengeTracker> {
    @Query(
        "select tradersChallengeTracker from TradersChallengeTracker tradersChallengeTracker where tradersChallengeTracker.user.login = ?#{principal.username}"
    )
    List<TradersChallengeTracker> findByUserIsCurrentUser();

    default Optional<TradersChallengeTracker> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TradersChallengeTracker> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TradersChallengeTracker> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct tradersChallengeTracker from TradersChallengeTracker tradersChallengeTracker left join fetch tradersChallengeTracker.mt4Account left join fetch tradersChallengeTracker.challengeType",
        countQuery = "select count(distinct tradersChallengeTracker) from TradersChallengeTracker tradersChallengeTracker"
    )
    Page<TradersChallengeTracker> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct tradersChallengeTracker from TradersChallengeTracker tradersChallengeTracker left join fetch tradersChallengeTracker.mt4Account left join fetch tradersChallengeTracker.challengeType"
    )
    List<TradersChallengeTracker> findAllWithToOneRelationships();

    @Query(
        "select tradersChallengeTracker from TradersChallengeTracker tradersChallengeTracker left join fetch tradersChallengeTracker.mt4Account left join fetch tradersChallengeTracker.challengeType where tradersChallengeTracker.id =:id"
    )
    Optional<TradersChallengeTracker> findOneWithToOneRelationships(@Param("id") Long id);
}
