package com.revature.SocialNetworkP2.controllers;

import com.revature.SocialNetworkP2.models.JsonResponse;
import com.revature.SocialNetworkP2.models.Post;
import com.revature.SocialNetworkP2.services.PageableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "pageable")
@CrossOrigin (origins = "http://localhost:4200")
public class PageableController {

    private PageableService pageableService;

    @Autowired
    public PageableController(PageableService pageableService){ this.pageableService = pageableService; }

    @GetMapping("/{userId}/{pageNumber}")
    public ResponseEntity<JsonResponse> getPagesByUserId(@PathVariable Integer userId, @PathVariable Integer pageNumber){
        List<Post> pagedPosts = this.pageableService.getAllPageablePostsByUserId(userId, pageNumber);

        if(pagedPosts.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new JsonResponse("wrong userId or page number", null));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new JsonResponse("User " + userId + " paged posts", pagedPosts));
    }

    @GetMapping("/{pageNumber}")
    public ResponseEntity<JsonResponse> getAllPageablePosts(@PathVariable Integer pageNumber){
        List<Post> pagedPosts = this.pageableService.getAllPageablePosts(pageNumber);

        if(pagedPosts == null){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new JsonResponse("no such posts found", null));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new JsonResponse("Paged posts retrieved", pagedPosts));
    }
}
