package com.revature.SocialNetworkP2.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.SocialNetworkP2.models.JsonResponse;
import com.revature.SocialNetworkP2.models.Post;
import com.revature.SocialNetworkP2.models.User;
import com.revature.SocialNetworkP2.services.PostService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(PostController.class)
class PostControllerIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostService postService;

//    Date date = new Date();
//    Timestamp timestamp = new Timestamp(date.getTime());
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @Test
    void createPostSuccess() throws Exception {
        Post requestBody = new Post(1, timestamp, "", "", null, null);
        Post resultPost = new Post(1, timestamp, "", "", null,  null);
        JsonResponse expectedResult = new JsonResponse("post created", resultPost);
        // todo figure out why mockito isn't working, use sout in controller to get return post
        Mockito.when(this.postService.createPost(requestBody)).thenReturn(resultPost);

        RequestBuilder request = MockMvcRequestBuilders.post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestBody));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void createPostFailure() throws Exception {
        Post requestBody = new Post(1, timestamp, "", "", null, null);
        Post resultPost = null;
        JsonResponse expectedResult = new JsonResponse("post not created", null);
        Mockito.when(this.postService.createPost(requestBody)).thenReturn(resultPost);

        RequestBuilder request = MockMvcRequestBuilders.post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestBody));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void getAllPostsSuccess() throws Exception {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1, timestamp, "", "", null, null));
        posts.add(new Post(2, timestamp, "", "", null, null));
        JsonResponse expectedResult = new JsonResponse("successful get request", posts);
        Mockito.when(this.postService.getAllPosts()).thenReturn(posts);

        RequestBuilder request = MockMvcRequestBuilders.get("/post");

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void getAllPostsNoPosts() throws Exception {
        List<Post> posts = null;
        JsonResponse expectedResult = new JsonResponse("bad request", posts);
        Mockito.when(this.postService.getAllPosts()).thenReturn(posts);

        mvc.perform(MockMvcRequestBuilders.get("/post"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void getAllPostsByUserIdValidId() throws Exception {
        User user = new User(1, "user2", "password", "email@example.com", "first", "last", null, null,null);
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1, timestamp, "", "", null, null));
        posts.add(new Post(2, timestamp, "", "", null, null));
        JsonResponse expectedResult = new JsonResponse("successful get request", posts);
        Mockito.when(this.postService.getAllByUserId(user.getUserId())).thenReturn(posts);

        RequestBuilder request = MockMvcRequestBuilders.get("/post/user/1");

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void getAllPostsByUserIdInvalidId() throws Exception {
        User user = new User(2, "user2", "password", "email@example.com", "first", "last", null, null,null);
        List<Post> posts = null;
        JsonResponse expectedResult = new JsonResponse("bad request", posts);
        Mockito.when(this.postService.getAllByUserId(user.getUserId())).thenReturn(posts);

        RequestBuilder request = MockMvcRequestBuilders.get("/post/user/2");

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));

    }
}