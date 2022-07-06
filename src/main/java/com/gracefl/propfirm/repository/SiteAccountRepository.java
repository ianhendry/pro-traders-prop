package com.gracefl.propfirm.repository;

import com.gracefl.propfirm.domain.SiteAccount;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SiteAccount entity.
 */
@Repository
public interface SiteAccountRepository extends JpaRepository<SiteAccount, Long>, JpaSpecificationExecutor<SiteAccount> {
    @Query("select siteAccount from SiteAccount siteAccount where siteAccount.user.login = ?#{principal.username}")
    List<SiteAccount> findByUserIsCurrentUser();

    default Optional<SiteAccount> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<SiteAccount> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<SiteAccount> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct siteAccount from SiteAccount siteAccount left join fetch siteAccount.user left join fetch siteAccount.addressDetails",
        countQuery = "select count(distinct siteAccount) from SiteAccount siteAccount"
    )
    Page<SiteAccount> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct siteAccount from SiteAccount siteAccount left join fetch siteAccount.user left join fetch siteAccount.addressDetails"
    )
    List<SiteAccount> findAllWithToOneRelationships();

    @Query(
        "select siteAccount from SiteAccount siteAccount left join fetch siteAccount.user left join fetch siteAccount.addressDetails where siteAccount.id =:id"
    )
    Optional<SiteAccount> findOneWithToOneRelationships(@Param("id") Long id);
}
