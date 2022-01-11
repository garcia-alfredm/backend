package com.revature.SocialNetworkP2.repository;

import com.revature.SocialNetworkP2.models.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PictureDao extends JpaRepository<Picture, Integer> {

    /*  Read for more information on derived queries
    *   https://www.baeldung.com/spring-data-derived-queries
    * */

    List<Picture> findAllByUserUserId(Integer userId);


}
