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

    /**
     * This method calls the picture repository layer to retrieve
     * all pictures
     * @return List of Pictures representing all pictures
     */
    public List<Picture> getAllPictures() {
        return this.pictureDao.findAll();
    }

    /**
     * This method calls the picture repository layer to retrieve one picture
     * @param pictureId Represents the id of the picture to retrieve
     * @return A Picture object representing the picture retrieved
     */
    public Picture getOnePicture(Integer pictureId){
        Picture picture = this.pictureDao.findById(pictureId).orElse(null);

        if(picture == null)
            return null;

        return picture;
    }

    /**
     * This method calls the picture repository layer to save a picture
     * @param picture a Picture object representing the picture to save
     * @return a Picture object representing picture saved
     */
    public Picture createOnePicture(Picture picture){
        return this.pictureDao.save(picture);
    }

    /**
     * This method calls the picture repository
     * to update the status of the profile picture
     * @param pictureId Represents the id of the picture to update
     * @return Picture object representing the picture updated
     */
    public Picture updatePictureFalse(Integer pictureId)
    {
        if(!this.pictureDao.existsById(pictureId))
            return null;

        Picture pic = this.pictureDao.getById(pictureId);
        pic.setProfilePicture(false);
        pic = this.pictureDao.save(pic);
        return pic;
    }

    /**
     * This method calls the picture repository layer
     * to update a picture as the profile picture
     * @param pictureId Represents the id of the picture to update
     * @return Picture object representing picture updated
     */
    public Picture updatePictureTrue(Integer pictureId)
    {
        if(!this.pictureDao.existsById(pictureId))
            return null;

        Picture pic = this.pictureDao.getById(pictureId);
        pic.setProfilePicture(true);
        pic = this.pictureDao.save(pic);
        return pic;
    }

    /**
     * Method calls the user repository layer to create
     * a new picture by the user id
     * @param picture
     * @param userId
     * @return
     */
    public Picture createPictureByUserId(Picture picture, Integer userId)
    {
        User user = this.userDao.getById(userId);
        if(user == null){
            return null;
        }
        picture.setUser(user);

        return this.pictureDao.save(picture);
    }

    /**
     * This method calls the user repository to
     * get the user by user id
     * @param userId Represents the user id
     * @return User object representing the user
     */
    public User getByUserId(Integer userId){
        User user = this.userDao.getById(userId);
        if(user == null)
            return null;

        return user;
    }

    /**
     * Calls the picture repository to delete a picture
     * @param pictureId Represents the id of the picture to delete
     * @return Boolean value
     */
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
