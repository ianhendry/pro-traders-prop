package com.gracefl.propfirm.web.rest;

import com.gracefl.propfirm.domain.BillingDetails;
import com.gracefl.propfirm.repository.BillingDetailsRepository;
import com.gracefl.propfirm.service.BillingDetailsQueryService;
import com.gracefl.propfirm.service.BillingDetailsService;
import com.gracefl.propfirm.service.criteria.BillingDetailsCriteria;
import com.gracefl.propfirm.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.gracefl.propfirm.domain.BillingDetails}.
 */
@RestController
@RequestMapping("/api")
public class BillingDetailsResource {

    private final Logger log = LoggerFactory.getLogger(BillingDetailsResource.class);

    private static final String ENTITY_NAME = "billingDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BillingDetailsService billingDetailsService;

    private final BillingDetailsRepository billingDetailsRepository;

    private final BillingDetailsQueryService billingDetailsQueryService;

    public BillingDetailsResource(
        BillingDetailsService billingDetailsService,
        BillingDetailsRepository billingDetailsRepository,
        BillingDetailsQueryService billingDetailsQueryService
    ) {
        this.billingDetailsService = billingDetailsService;
        this.billingDetailsRepository = billingDetailsRepository;
        this.billingDetailsQueryService = billingDetailsQueryService;
    }

    /**
     * {@code POST  /billing-details} : Create a new billingDetails.
     *
     * @param billingDetails the billingDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new billingDetails, or with status {@code 400 (Bad Request)} if the billingDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/billing-details")
    public ResponseEntity<BillingDetails> createBillingDetails(@RequestBody BillingDetails billingDetails) throws URISyntaxException {
        log.debug("REST request to save BillingDetails : {}", billingDetails);
        if (billingDetails.getId() != null) {
            throw new BadRequestAlertException("A new billingDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BillingDetails result = billingDetailsService.save(billingDetails);
        return ResponseEntity
            .created(new URI("/api/billing-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /billing-details/:id} : Updates an existing billingDetails.
     *
     * @param id the id of the billingDetails to save.
     * @param billingDetails the billingDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated billingDetails,
     * or with status {@code 400 (Bad Request)} if the billingDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the billingDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/billing-details/{id}")
    public ResponseEntity<BillingDetails> updateBillingDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BillingDetails billingDetails
    ) throws URISyntaxException {
        log.debug("REST request to update BillingDetails : {}, {}", id, billingDetails);
        if (billingDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, billingDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!billingDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BillingDetails result = billingDetailsService.update(billingDetails);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, billingDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /billing-details/:id} : Partial updates given fields of an existing billingDetails, field will ignore if it is null
     *
     * @param id the id of the billingDetails to save.
     * @param billingDetails the billingDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated billingDetails,
     * or with status {@code 400 (Bad Request)} if the billingDetails is not valid,
     * or with status {@code 404 (Not Found)} if the billingDetails is not found,
     * or with status {@code 500 (Internal Server Error)} if the billingDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/billing-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BillingDetails> partialUpdateBillingDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BillingDetails billingDetails
    ) throws URISyntaxException {
        log.debug("REST request to partial update BillingDetails partially : {}, {}", id, billingDetails);
        if (billingDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, billingDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!billingDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BillingDetails> result = billingDetailsService.partialUpdate(billingDetails);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, billingDetails.getId().toString())
        );
    }

    /**
     * {@code GET  /billing-details} : get all the billingDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of billingDetails in body.
     */
    @GetMapping("/billing-details")
    public ResponseEntity<List<BillingDetails>> getAllBillingDetails(BillingDetailsCriteria criteria) {
        log.debug("REST request to get BillingDetails by criteria: {}", criteria);
        List<BillingDetails> entityList = billingDetailsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /billing-details/count} : count all the billingDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/billing-details/count")
    public ResponseEntity<Long> countBillingDetails(BillingDetailsCriteria criteria) {
        log.debug("REST request to count BillingDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(billingDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /billing-details/:id} : get the "id" billingDetails.
     *
     * @param id the id of the billingDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the billingDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/billing-details/{id}")
    public ResponseEntity<BillingDetails> getBillingDetails(@PathVariable Long id) {
        log.debug("REST request to get BillingDetails : {}", id);
        Optional<BillingDetails> billingDetails = billingDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(billingDetails);
    }

    /**
     * {@code DELETE  /billing-details/:id} : delete the "id" billingDetails.
     *
     * @param id the id of the billingDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/billing-details/{id}")
    public ResponseEntity<Void> deleteBillingDetails(@PathVariable Long id) {
        log.debug("REST request to delete BillingDetails : {}", id);
        billingDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
