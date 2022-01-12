package com.revature.SocialNetworkP2.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.SocialNetworkP2.models.JsonResponse;
import com.revature.SocialNetworkP2.models.Post;
import com.revature.SocialNetworkP2.services.PostService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(PostController.class)
class PostControllerIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostService postService;

    @MockBean
    private MockHttpSession session;

    Date date = new Date();
    Timestamp timestamp = new Timestamp(date.getTime());
    String time = timestamp.toString();

    @Test
    void createPostSuccess() throws Exception {
//        Post post = new Post(1, timestamp, "", "", null, null);
//        Post resultPost = new Post(1, timestamp, "", "", null, null);
//        JsonResponse expectedResult = new JsonResponse("post created", resultPost);
//        Mockito.when(this.postService.createPost(post)).thenReturn(resultPost);
//
//        RequestBuilder request = MockMvcRequestBuilders.post("/post")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(post));
//
//        mvc.perform(request)
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void getAllPosts() {
    }

    @Test
    void getAllPostsByUserId() {
    }
}