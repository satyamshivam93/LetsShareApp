package com.app.share.service;

import com.app.share.domain.MyPostResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link MyPostResponse}.
 */
public interface MyPostResponseService {

    /**
     * Save a myPostResponse.
     *
     * @param myPostResponse the entity to save.
     * @return the persisted entity.
     */
    MyPostResponse save(MyPostResponse myPostResponse);

    /**
     * Get all the myPostResponses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MyPostResponse> findAll(Pageable pageable);


    /**
     * Get the "id" myPostResponse.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MyPostResponse> findOne(Long id);

    /**
     * Delete the "id" myPostResponse.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
