package com.revature.SocialNetworkP2.repository;

import com.revature.SocialNetworkP2.models.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageableDao extends JpaRepository<Post, Integer> {

    Slice<Post> findByUserUserId(Integer userId, Pageable pageable);
}
