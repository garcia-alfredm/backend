package com.revature.SocialNetworkP2.repository;

import com.revature.SocialNetworkP2.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostDao extends JpaRepository<Post, Integer> {

    /*  Read for more information on derived queries
     *   https://www.baeldung.com/spring-data-derived-queries
     * */

    List<Post> findAllByUserUserId(Integer userId);
}
