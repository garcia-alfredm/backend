package com.revature.SocialNetworkP2.services;

import com.revature.SocialNetworkP2.models.Post;
import com.revature.SocialNetworkP2.repository.PageableDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PageableService {

    private PageableDao pageableDao;

    @Autowired
    public PageableService(PageableDao pageableDao){ this.pageableDao = pageableDao; }

    public List<Post> getAllPageablePostsByUserId(Integer userId, Integer pageNumber){
        int pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("postId").descending());

        Slice<Post> pagedposts = this.pageableDao.findByUserUserId(userId, pageable);
        return pagedposts.toList();

    }

    public List<Post> getAllPageablePosts(Integer pageNumber){
        int pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("postId").descending());

        Page<Post> pagedposts = this.pageableDao.findAll(pageable);

        if(pagedposts == null){
            return null;
        }

        return pagedposts.toList();
    }
}
