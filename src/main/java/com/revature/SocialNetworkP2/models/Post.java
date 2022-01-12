package com.revature.SocialNetworkP2.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity //marks the class as one that is going to be a table schema
@Table(name = "posts") // defines the name of the table
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer postId;

    @Column
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Timestamp submitted;

    @Column
    private String content;

    @Column
    private String pictureLink;

    @OneToMany(targetEntity = Picture.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "posts_pictures_fk", referencedColumnName = "postId")
    private List<Picture> picture;

    @JsonIgnoreProperties({"pictures", "posts", "password", "firstname", "lastname"})
    @ManyToOne
    private User user;
}
