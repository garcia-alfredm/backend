package com.revature.SocialNetworkP2.repository;

import com.revature.SocialNetworkP2.util.H2Util;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoIT {

    UserDao userDao;

    UserDaoIT(){
        // without dao implementation class, there is no way to do integration testing of repository
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findByUsername() {
    }
}