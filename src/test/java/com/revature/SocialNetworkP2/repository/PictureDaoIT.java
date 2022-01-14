package com.revature.SocialNetworkP2.repository;

import com.revature.SocialNetworkP2.models.Picture;
import com.revature.SocialNetworkP2.models.Post;
import com.revature.SocialNetworkP2.models.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PictureDaoIT {

    @Autowired
    PictureDao pictureDao;

    @Autowired
    UserDao userDao;

    @Test
    void findAllByUserId() {
        List<Post> posts = new ArrayList<>();
        List<Picture> pictures = new ArrayList<>();
        String token = null;
        User user = new User(1, "alincoln", "password", "alincoln@email.com", "Abraham", "Lincoln", token, posts, pictures);
        this.userDao.save(user);
        this.pictureDao.save(new Picture(1,"", false, user));
        this.pictureDao.save(new Picture(2,"", false, user));
        List<Picture> expectedResults = new ArrayList<>();
        expectedResults.add(new Picture(1,"", false, user));
        expectedResults.add(new Picture(2,"", false, user));

        List<Picture> actualResults = this.pictureDao.findAllByUserUserId(user.getUserId());

        assertEquals(expectedResults, actualResults);
    }
}