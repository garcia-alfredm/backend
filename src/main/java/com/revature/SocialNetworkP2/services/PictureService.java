package com.revature.SocialNetworkP2.services;

import com.revature.SocialNetworkP2.models.Picture;
import com.revature.SocialNetworkP2.models.User;
import com.revature.SocialNetworkP2.repository.PictureDao;
import com.revature.SocialNetworkP2.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PictureService {

    private PictureDao pictureDao;
    private UserDao userDao;

    /***************************   CONSTRUCTOR   ***************************/

    @Autowired
    public PictureService(PictureDao pictureDao , UserDao userDao){ this.pictureDao = pictureDao;this.userDao = userDao;}

    /******************************   METHODS   ******************************/

    public List<Picture> getAllPictures() {
        return this.pictureDao.findAll();
    }

    public Picture getOnePicture(Integer pictureId){
        Picture picture = this.pictureDao.findById(pictureId).orElse(null);

        if(picture == null)
            return null;

        return picture;
    }

    public Picture createOnePicture(Picture picture){
        return this.pictureDao.save(picture);
    }

    public Picture updatePictureFalse(Integer pictureId)
    {
        if(!this.pictureDao.existsById(pictureId))
            return null;

        Picture pic = this.pictureDao.getById(pictureId);
        pic.setProfilePicture(false);
        pic = this.pictureDao.save(pic);
        return pic;
    }

    public Picture updatePictureTrue(Integer pictureId)
    {
        if(!this.pictureDao.existsById(pictureId))
            return null;

        Picture pic = this.pictureDao.getById(pictureId);
        pic.setProfilePicture(true);
        pic = this.pictureDao.save(pic);
        return pic;
    }

    public Picture createPictureByUserId(Picture picture, Integer userId)
    {
        User user = this.userDao.getById(userId);
        if(user == null){
            return null;
        }
        picture.setUser(user);

        return this.pictureDao.save(picture);
    }

    public User getByUserId(Integer userId){
        User user = this.userDao.getById(userId);
        if(user == null)
            return null;

        return user;
    }

    public Boolean deletePicture(Integer pictureId){
        if(getOnePicture(pictureId) == null)
            return false;

        this.pictureDao.deleteById(pictureId);

        return true;
    }

    /******************************   DERIVED QUERY METHODS   ******************************/

    public List<Picture> getAllByUserId(Integer userId){
        return this.pictureDao.findAllByUserUserId(userId);
    }
}
