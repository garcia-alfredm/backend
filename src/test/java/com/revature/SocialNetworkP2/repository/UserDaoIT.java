package com.revature.SocialNetworkP2.repository;

import com.revature.SocialNetworkP2.models.Picture;
import com.revature.SocialNetworkP2.models.Post;
import com.revature.SocialNetworkP2.models.User;
import com.revature.SocialNetworkP2.util.H2Util;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserDaoIT {

    @Autowired
    UserDao userDao;

    @BeforeAll
    void setup(){
        List<Post> posts = new ArrayList<>();
        List<Picture> pictures = new ArrayList<>();
        String token = null;
        User expectedValue = new User(1, "alincoln", "password", "alincoln@email.com", "Abraham", "Lincoln", token, posts, pictures);
        this.userDao.save(expectedValue);
    }

    @Test
    void findByUsername() {
        List<Post> posts = new ArrayList<>();
        List<Picture> pictures = new ArrayList<>();
        String token = null;
        User expectedValue = new User(1, "alincoln", "password", "alincoln@email.com", "Abraham", "Lincoln", token, posts, pictures);
        //this.userDao.save(expectedValue);

        User actualValue = this.userDao.findByUsername(expectedValue.getUsername());

        assertEquals(expectedValue, actualValue);
    }

    @Test
    void findByEmail(){
        List<Post> posts = new ArrayList<>();
        List<Picture> pictures = new ArrayList<>();
        String token = null;
        User expectedValue = new User(1, "alincoln", "password", "alincoln@email.com", "Abraham", "Lincoln", token, posts, pictures);
        //this.userDao.save(expectedValue);

        User actualValue = this.userDao.findByEmail("alincoln@email.com");

        assertEquals(expectedValue, actualValue);
    }
}