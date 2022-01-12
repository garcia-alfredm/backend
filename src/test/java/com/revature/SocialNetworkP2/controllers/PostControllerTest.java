package com.revature.SocialNetworkP2.controllers;

import com.revature.SocialNetworkP2.models.JsonResponse;
import com.revature.SocialNetworkP2.models.Post;
import com.revature.SocialNetworkP2.models.User;
import com.revature.SocialNetworkP2.services.PostService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostControllerTest {

    PostService postService = Mockito.mock(PostService.class);
    PostController postController;

    public PostControllerTest(){ this.postController = new PostController(this.postService);}

    @Test
    void createPostSuccess() {
        Post post = new Post(1, new Timestamp(200L), "", "", null, null);
        Post resultPost = new Post(1, new Timestamp(200L), "", "", null, null);
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.ok(new JsonResponse("post created", resultPost));
        Mockito.when(this.postService.createPost(post)).thenReturn(resultPost);

        ResponseEntity<JsonResponse> actualResult = this.postController.createPost(post);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createPostFailure() {
        Post post = new Post(1, new Timestamp(200L), "", "", null, null);
        Post resultPost = null;
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponse("post not created", null));
        Mockito.when(this.postService.createPost(post)).thenReturn(resultPost);

        ResponseEntity<JsonResponse> actualResult = this.postController.createPost(post);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getAllPostsSuccessful() {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1, new Timestamp(200L), "", "", null, null));
        posts.add(new Post(2, new Timestamp(200L), "", "", null, null));
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.ok(new JsonResponse("successful get request", posts));
        Mockito.when(this.postService.getAllPosts()).thenReturn(posts);

        ResponseEntity<JsonResponse> actualResult = this.postController.getAllPosts();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getAllPostsNoPosts() {
        List<Post> posts = null;
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponse("bad request", null));
        Mockito.when(this.postService.getAllPosts()).thenReturn(posts);

        ResponseEntity<JsonResponse> actualResult = this.postController.getAllPosts();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getAllPostsByUserIdValidId() {
        User user = new User(2, "user2", "password", "email@example.com", "first", "last", null, null,null);
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1, new Timestamp(200L), "", "", null, null));
        posts.add(new Post(2, new Timestamp(200L), "", "", null, null));
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.ok(new JsonResponse("successful get request", posts));
        Mockito.when(this.postService.getAllByUserId(user.getUserId())).thenReturn(posts);

        ResponseEntity<JsonResponse> actualResult = this.postController.getAllPostsByUserId(user.getUserId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getAllPostsByUserIdInvalidId() {
        User user = new User(2, "user2", "password", "email@example.com", "first", "last", null, null,null);
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1, new Timestamp(200L), "", "", null, null));
        posts.add(new Post(2, new Timestamp(200L), "", "", null, null));
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponse("bad request", null));
        Mockito.when(this.postService.getAllByUserId(user.getUserId())).thenReturn(null);

        ResponseEntity<JsonResponse> actualResult = this.postController.getAllPostsByUserId(user.getUserId());

        assertEquals(expectedResult, actualResult);
    }

}