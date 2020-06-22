package com.app.share.web.rest;

import com.app.share.domain.MyPostResponse;
import com.app.share.service.MyPostResponseService;
import com.app.share.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.app.share.domain.MyPostResponse}.
 */
@RestController
@RequestMapping("/api")
public class MyPostResponseResource {

	private final Logger log = LoggerFactory.getLogger(MyPostResponseResource.class);

	private static final String ENTITY_NAME = "myPostResponse";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final MyPostResponseService myPostResponseService;

	public MyPostResponseResource(MyPostResponseService myPostResponseService) {
		this.myPostResponseService = myPostResponseService;
	}

	/**
	 * {@code POST  /my-post-responses} : Create a new myPostResponse.
	 *
	 * @param myPostResponse the myPostResponse to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new myPostResponse, or with status {@code 400 (Bad Request)}
	 *         if the myPostResponse has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/my-post-responses")
	public ResponseEntity<MyPostResponse> createMyPostResponse(@RequestBody MyPostResponse myPostResponse)
			throws URISyntaxException {
		log.debug("REST request to save MyPostResponse : {}", myPostResponse);
		if (myPostResponse.getId() != null) {
			throw new BadRequestAlertException("A new myPostResponse cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		MyPostResponse result = myPostResponseService.save(myPostResponse);
		return ResponseEntity
				.created(new URI("/api/my-post-responses/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /my-post-responses} : Updates an existing myPostResponse.
	 *
	 * @param myPostResponse the myPostResponse to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated myPostResponse, or with status {@code 400 (Bad Request)}
	 *         if the myPostResponse is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the myPostResponse couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/my-post-responses")
	public ResponseEntity<MyPostResponse> updateMyPostResponse(@RequestBody MyPostResponse myPostResponse)
			throws URISyntaxException {
		log.debug("REST request to update MyPostResponse : {}", myPostResponse);
		if (myPostResponse.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		MyPostResponse result = myPostResponseService.save(myPostResponse);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
				myPostResponse.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /my-post-responses} : get all the myPostResponses.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of myPostResponses in body.
	 */
	@GetMapping("/my-post-responses")
	public ResponseEntity<List<MyPostResponse>> getAllMyPostResponses(Pageable pageable) {
		log.debug("REST request to get a page of MyPostResponses");
		Page<MyPostResponse> page = myPostResponseService.findAll(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/*    *//**
			 * {@code GET  /my-post-responses} : get all the myPostResponses.
			 *
			 * @param pageable the pagination information.
			 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
			 *         of myPostResponses in body.
			 *//*
				 * @GetMapping("/my-post-responses") public ResponseEntity<List<MyPostResponse>>
				 * getAllMyPostResponses(Pageable pageable) {
				 * log.debug("REST request to get a page of MyPostResponses");
				 * Page<MyPostResponse> page = myPostResponseService.findAll(pageable);
				 * HttpHeaders headers =
				 * PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.
				 * fromCurrentRequest(), page); return
				 * ResponseEntity.ok().headers(headers).body(page.getContent()); }
				 */

	/**
	 * {@code GET  /my-post-responses/:id} : get the "id" myPostResponse.
	 *
	 * @param id the id of the myPostResponse to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the myPostResponse, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/my-post-responses/{id}")
	public ResponseEntity<MyPostResponse> getMyPostResponse(@PathVariable Long id) {
		log.debug("REST request to get MyPostResponse : {}", id);
		Optional<MyPostResponse> myPostResponse = myPostResponseService.findOne(id);
		return ResponseUtil.wrapOrNotFound(myPostResponse);
	}

	/**
	 * {@code DELETE  /my-post-responses/:id} : delete the "id" myPostResponse.
	 *
	 * @param id the id of the myPostResponse to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/my-post-responses/{id}")
	public ResponseEntity<Void> deleteMyPostResponse(@PathVariable Long id) {
		log.debug("REST request to delete MyPostResponse : {}", id);
		myPostResponseService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}
}
