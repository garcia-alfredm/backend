package com.revature.SocialNetworkP2.services;

import com.revature.SocialNetworkP2.models.Post;
import com.revature.SocialNetworkP2.repository.PostDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PostService {

    private PostDao postDao;

    /***************************   CONSTRUCTOR   ***************************/

    @Autowired
    public PostService(PostDao postDao){
        this.postDao = postDao;
    }

    /******************************   METHODS   ******************************/

    /**
     * This method calls the post repository layer
     * and returns all posts
     * @return a List of all Posts
     */
    public List<Post> getAllPosts(){
        return this.postDao.findAll();
    }

    /**
     * This method call the post repository layer
     * to get one post by id
     * @param postId Represents the id of the post
     * @return a Post object representing the post
     */
    public Post getPost(Integer postId){
        return this.postDao.findById(postId).orElse(null);
    }

    /**
     * This method calls the post repository layer
     * to save a new post
     * @param post A Post object representing the post to save
     * @return Returns the post saved to the db
     */
    public Post createPost(Post post) {
        return this.postDao.save(post);
    }

    /**
     * This method calls the post repository to update a post
     * @param post Parameter represents the post to update
     * @return An object representing the Post saved
     */
    public Post updatePost(Post post){
        if(getPost(post.getPostId()) == null)
            return null;

        return this.postDao.save(post);
    }

    /**
     * This method calls the post repository
     * to delete a post by it's id number
     * @param postId the post's id number
     * @return a boolean value representing success, failure
     */
    public Boolean deletePost(Integer postId){
        if(getPost(postId) == null)
            return false;

        this.postDao.deleteById(postId);
        return true;
    }

    /******************************   DERIVED QUERY METHODS   ******************************/

    /**
     * Derived query method. Gets all posts by the user's id number
     * @param userId Parameter represents ther user's id number
     * @return List of type Post, representing all of a user's post
     */
    public List<Post> getAllByUserId(Integer userId){
        return this.postDao.findAllByUserUserId(userId);
    }
}
