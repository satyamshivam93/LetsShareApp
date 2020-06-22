package com.app.share.repository;

import com.app.share.domain.MyPost;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MyPost entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MyPostRepository extends JpaRepository<MyPost, Long> {
}
