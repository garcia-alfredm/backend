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

    public List<Post> getAllPosts(){
        return this.postDao.findAll();
    }

    public Post getPost(Integer postId){
        return this.postDao.findById(postId).orElse(null);
    }

    public Post createPost(Post post) {
        return this.postDao.save(post);
    }

    public Post updatePost(Post post){
        if(getPost(post.getPostId()) == null)
            return null;

        return this.postDao.save(post);
    }

    public Boolean deletePost(Integer postId){
        if(getPost(postId) == null)
            return false;

        this.postDao.deleteById(postId);
        return true;
    }

    /******************************   DERIVED QUERY METHODS   ******************************/

    public List<Post> getAllByUserId(Integer userId){
        return this.postDao.findAllByUserUserId(userId);
    }
}
