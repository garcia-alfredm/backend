package com.revature.SocialNetworkP2.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity //marks the class as one that is going to be a table schema
@Table(name = "pictures") // defines the name of the table
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pictureId;

    @Column(nullable = false)
    private String pictureLink;

    @Column(columnDefinition="BOOLEAN DEFAULT false")
    private Boolean profilePicture;

    @JsonIgnoreProperties({"pictures", "posts", "password", "firstname", "lastname"})
    @ManyToOne
    private User user;
}
