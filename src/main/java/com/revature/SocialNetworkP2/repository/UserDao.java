package com.revature.SocialNetworkP2.repository;

import com.revature.SocialNetworkP2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {

    User findByUsername(String username);
    User findByEmail (String email);

    User findByResetPasswordToken(String token);

}
