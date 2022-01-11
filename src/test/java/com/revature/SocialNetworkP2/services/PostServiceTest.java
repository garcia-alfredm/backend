package com.revature.SocialNetworkP2.services;

import com.revature.SocialNetworkP2.models.Post;
import com.revature.SocialNetworkP2.repository.PictureDao;
import com.revature.SocialNetworkP2.repository.PostDao;
import com.revature.SocialNetworkP2.repository.UserDao;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PostServiceTest {

    PostDao postDao = Mockito.mock(PostDao.class);

    PostService postService;

    public PostServiceTest() { this.postService = new PostService(postDao); }

    @Test
    void getAllPosts(){
        List<Post> expectedResults = new ArrayList<>();
        expectedResults.add(new Post(1, new Timestamp(12L), "This a one post", "", null, null ));
        expectedResults.add(new Post(2, new Timestamp(300L), "This a one post", "", null, null ));
        Mockito.when(this.postDao.findAll()).thenReturn(expectedResults);

        List<Post> actualResults = this.postService.getAllPosts();

        assertEquals(expectedResults, actualResults);
    }

    @Test
    void getAllByUserId() {
        // test userId
        Integer userId = 1;
        List<Post> posts = new ArrayList<>();

        // adding new posts with (userId = 1) to list of posts
        Post post1 = new Post();
        posts.add(post1);

        Post post2 = new Post();
        posts.add(post2);

        Post post3 = new Post();
        posts.add(post3);

        Mockito.when(this.postDao.findAllByUserUserId(userId)).thenReturn(posts);

        // expected
        List<Post> expectedResult = posts;

        // actual
        List<Post> actualResult = postService.getAllByUserId(userId);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getPostInvalidId(){
        Post post = new Post(1, new Timestamp(12L), "This a one post", "", null, null );
        Mockito.when(this.postDao.findById(post.getPostId())).thenReturn(Optional.ofNullable(null));

        Post actualResult = this.postService.getPost(2);

        assertNull(actualResult);
    }

    @Test
    public void getPostValidId(){
        Post expectedResult = new Post(1, new Timestamp(12L), "This a one post", "", null, null );
        Mockito.when(this.postDao.findById(expectedResult.getPostId())).thenReturn(Optional.of(expectedResult));

        Post actualResult = this.postService.getPost(expectedResult.getPostId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createPost(){
        Post expectedResult = new Post(1, new Timestamp(12L), "This a one post", "", null, null );
        Mockito.when(this.postDao.save(expectedResult)).thenReturn(expectedResult);

        Post actualResults = this.postService.createPost(expectedResult);

        assertEquals(expectedResult, actualResults);
        Mockito.verify(this.postDao, Mockito.times(1)).save(expectedResult);
    }

    @Test
    public void updatePostNoSuchPostExists(){
        Post post = new Post(1, new Timestamp(12L), "This a one post", "", null, null );
        Mockito.when(this.postDao.findById(2)).thenReturn(Optional.ofNullable(null));

        Post actualResult = this.postService.updatePost(new Post(1, new Timestamp(12L), "updated post content", "", null, null ));

        assertNull(actualResult);
    }

    @Test
    public void updatePostSuccessful(){
        Post post = new Post(1, new Timestamp(12L), "This a one post", "", null, null );
        Post expectedResult = new Post(1, new Timestamp(12L), "updated post content", "", null, null );
        Mockito.when(this.postDao.findById(1)).thenReturn(Optional.of(expectedResult));
        Mockito.when(this.postDao.save(expectedResult)).thenReturn(expectedResult);

        Post actualResult = this.postService.updatePost(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void deletePostNoSuchPostExists(){
        Post post = new Post(1, new Timestamp(12L), "This a one post", "", null, null );
        Mockito.when(this.postDao.findById(2)).thenReturn(Optional.ofNullable(null));

        Boolean actualResult = this.postService.deletePost(2);

        assertFalse(actualResult);
    }

    @Test
    public void deletePostSuccessful(){
        Boolean expectedResult = true;
        Post post = new Post(1, new Timestamp(12L), "This a one post", "", null, null );
        Mockito.when(this.postDao.findById(post.getPostId())).thenReturn(Optional.of(post));

        Boolean actualResult = this.postService.deletePost(post.getPostId());

        assertEquals(expectedResult, actualResult);
        Mockito.verify(this.postDao, Mockito.times(1)).deleteById(post.getPostId());
    }
}