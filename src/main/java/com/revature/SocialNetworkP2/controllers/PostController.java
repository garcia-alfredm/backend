package com.revature.SocialNetworkP2.controllers;

import com.revature.SocialNetworkP2.models.JsonResponse;
import com.revature.SocialNetworkP2.models.Post;
import com.revature.SocialNetworkP2.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "post")
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<JsonResponse> createPost(@RequestBody Post post){
        Post resultPost = this.postService.createPost(post);

        if(resultPost == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponse("post not created", null));

        return ResponseEntity.ok(new JsonResponse("post created", post));
    }

    @GetMapping
    public ResponseEntity<JsonResponse> getAllPosts() {
        List<Post> posts = this.postService.getAllPosts();

        if(posts == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponse("bad request", null));

        return ResponseEntity.ok(new JsonResponse("successful get request", posts));
    }

    @GetMapping(value = "{postId}")
    public ResponseEntity<JsonResponse> getOnePost(@PathVariable Integer postId) {
        Post post = this.postService.getPost(postId);

        if(post == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponse("bad request", null));

        return ResponseEntity.ok(new JsonResponse("successful get request", post));
    }

    @GetMapping(value = "user/{userId}")
    public ResponseEntity<JsonResponse> getAllPostsByUserId(@PathVariable Integer userId){
        List<Post> posts = this.postService.getAllByUserId(userId);

        if(posts == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponse("bad request", null));

        return ResponseEntity.ok(new JsonResponse("successful get request", posts));
    }

    @DeleteMapping(value="{postId}")
    public ResponseEntity<JsonResponse> deletePost(@PathVariable Integer postId) {

        if (!this.postService.deletePost(postId)) // if deletePost returns FALSE
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new JsonResponse("There is not a post with ID " + postId, null));

        return ResponseEntity.ok(new JsonResponse("Post with ID " + postId + " was removed", null));
    }
}
