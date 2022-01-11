package com.revature.SocialNetworkP2.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
* What is JPA?
*   - Java Persistence API
*   - Collection of classes to persistently store data to your database
*
* What is ORM?
*   - Object Relational Mapping // classes can be directly mapped to database schema
*   - ORM's can take classes and their variables and translate them into DB table schemas
* */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity //marks the class as one that is going to be a table schema
@Table(name = "users") // defines the name of the table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer userId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = true, name = "reset_password_token")
    private String resetPasswordToken;

    @OneToMany(targetEntity = Post.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "users_posts_fk", referencedColumnName = "userId")
    private List<Post> posts;

    @OneToMany(targetEntity = Picture.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "users_pictures_fk", referencedColumnName = "userId")
    private List<Picture> pictures;

    public String getFullname(){
        return getFirstname() + " " + getLastname();
    }
}
