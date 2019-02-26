package co.gov.udistrital.volley.web.rest;
import co.gov.udistrital.volley.domain.Membership;
import co.gov.udistrital.volley.service.MembershipService;
import co.gov.udistrital.volley.web.rest.errors.BadRequestAlertException;
import co.gov.udistrital.volley.web.rest.util.HeaderUtil;
import co.gov.udistrital.volley.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Membership.
 */
@RestController
@RequestMapping("/api")
public class MembershipResource {

    private final Logger log = LoggerFactory.getLogger(MembershipResource.class);

    private static final String ENTITY_NAME = "membership";

    private final MembershipService membershipService;

    public MembershipResource(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    /**
     * POST  /memberships : Create a new membership.
     *
     * @param membership the membership to create
     * @return the ResponseEntity with status 201 (Created) and with body the new membership, or with status 400 (Bad Request) if the membership has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/memberships")
    public ResponseEntity<Membership> createMembership(@Valid @RequestBody Membership membership) throws URISyntaxException {
        log.debug("REST request to save Membership : {}", membership);
        if (membership.getId() != null) {
            throw new BadRequestAlertException("A new membership cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Membership result = membershipService.save(membership);
        return ResponseEntity.created(new URI("/api/memberships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /memberships : Updates an existing membership.
     *
     * @param membership the membership to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated membership,
     * or with status 400 (Bad Request) if the membership is not valid,
     * or with status 500 (Internal Server Error) if the membership couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/memberships")
    public ResponseEntity<Membership> updateMembership(@Valid @RequestBody Membership membership) throws URISyntaxException {
        log.debug("REST request to update Membership : {}", membership);
        if (membership.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Membership result = membershipService.save(membership);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, membership.getId().toString()))
            .body(result);
    }

    /**
     * GET  /memberships : get all the memberships.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of memberships in body
     */
    @GetMapping("/memberships")
    public ResponseEntity<List<Membership>> getAllMemberships(Pageable pageable) {
        log.debug("REST request to get a page of Memberships");
        Page<Membership> page = membershipService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/memberships");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /memberships/:id : get the "id" membership.
     *
     * @param id the id of the membership to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the membership, or with status 404 (Not Found)
     */
    @GetMapping("/memberships/{id}")
    public ResponseEntity<Membership> getMembership(@PathVariable Long id) {
        log.debug("REST request to get Membership : {}", id);
        Optional<Membership> membership = membershipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(membership);
    }

    /**
     * DELETE  /memberships/:id : delete the "id" membership.
     *
     * @param id the id of the membership to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/memberships/{id}")
    public ResponseEntity<Void> deleteMembership(@PathVariable Long id) {
        log.debug("REST request to delete Membership : {}", id);
        membershipService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
