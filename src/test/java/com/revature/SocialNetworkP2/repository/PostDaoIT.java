package com.revature.SocialNetworkP2.repository;

import com.revature.SocialNetworkP2.models.Picture;
import com.revature.SocialNetworkP2.models.Post;
import com.revature.SocialNetworkP2.models.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostDaoIT {

    @Autowired
    PostDao postDao;

    @Autowired
    UserDao userDao;

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    //timestamp.toInstant().toLocalDate()

    @Test
    void findAllByUserId() {
        List<Post> posts = new ArrayList<>();
        List<Picture> pictures = new ArrayList<>();
        String token = null;
        User user = new User(1, "alincoln", "password", "alincoln@email.com", "Abraham", "Lincoln", token, posts, pictures);
        this.userDao.save(user);
        this.postDao.save(new Post(1, timestamp, "Test post", "dummylink.com/picutre", null, user));
        this.postDao.save(new Post(2, timestamp, "2nd post", "dummylink.com/picutre2", null, user));
        List<Post> expectedResults = new ArrayList<>();
        expectedResults.add(new Post(1, timestamp, "Test post", "dummylink.com/picutre", null, user));
        expectedResults.add(new Post(2, timestamp, "2nd post", "dummylink.com/picutre2", null, user));

        List<Post> actualResults = this.postDao.findAll();

        assertEquals(expectedResults, actualResults);
    }
}