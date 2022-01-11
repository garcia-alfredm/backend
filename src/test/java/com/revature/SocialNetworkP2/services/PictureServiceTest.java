package com.revature.SocialNetworkP2.services;

import com.revature.SocialNetworkP2.models.Picture;
import com.revature.SocialNetworkP2.models.User;
import com.revature.SocialNetworkP2.repository.PictureDao;
import com.revature.SocialNetworkP2.repository.UserDao;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PictureServiceTest {

    PictureDao pictureDao = Mockito.mock(PictureDao.class);
    UserDao userDao = Mockito.mock(UserDao.class);
    PictureService pictureService;

    public PictureServiceTest() {this.pictureService = new PictureService(pictureDao,userDao);}

    @Test
    public void getAllPictures(){
        List<Picture> pictures = new ArrayList<>();
        pictures.add(new Picture(1, "dummylink.com/image1", false, null));
        pictures.add(new Picture(2, "dummylink.com/image2", false, null));
        Mockito.when(this.pictureDao.findAll()).thenReturn(pictures);

        List<Picture> actualResults = this.pictureService.getAllPictures();

        assertEquals(pictures, actualResults);
    }

    @Test
    public void getOnePictureInvalidId(){
        Picture picture = new Picture(1, "dummylink.com/image1", false, null);
        Mockito.when(this.pictureDao.findById(2)).thenReturn(Optional.ofNullable(null));

        Picture actualResult = this.pictureService.getOnePicture(2);

        assertNull(actualResult);
    }

    @Test
    public void getOnePictureValidId(){
        Picture expectedResult = new Picture(1, "dummylink.com/image1", false, null);
        Mockito.when(this.pictureDao.findById(expectedResult.getPictureId())).thenReturn(Optional.of(expectedResult));

        Picture actualResult = this.pictureService.getOnePicture(expectedResult.getPictureId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void createOnePicture(){
        Picture expectedResult = new Picture(1, "dummylink.com/image1", false, null);
        Mockito.when(this.pictureDao.save(expectedResult)).thenReturn(expectedResult);

        Picture actualResult = this.pictureService.createOnePicture(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void updatePictureFalsePictureDoesNotExists(){
        Picture picture = new Picture(1, "dummylink.com/image1", true, null);
        Mockito.when(this.pictureDao.existsById(2)).thenReturn(false);

        Picture actualResult = this.pictureService.updatePictureFalse(2);

        assertNull(actualResult);
    }

    @Test
    public void updatePictureFalsePictureExists(){
        Picture picture = new Picture(1, "dummylink.com/image1", true, null);
        Picture expectedResult = new Picture(1, "dummylink.com/image1", false, null);
        Mockito.when(this.pictureDao.existsById(picture.getPictureId())).thenReturn(true);
        Mockito.when(this.pictureDao.getById(picture.getPictureId())).thenReturn(picture);
        Mockito.when(this.pictureDao.save(picture)).thenReturn(expectedResult);

        Picture actualResult = this.pictureService.updatePictureFalse(picture.getPictureId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void updatePictureTruePictureDoesNotExists(){
        Picture picture = new Picture(1, "dummylink.com/image1", false, null);
        Mockito.when(this.pictureDao.existsById(2)).thenReturn(false);

        Picture actualResult = this.pictureService.updatePictureTrue(2);

        assertNull(actualResult);
    }

    @Test
    public void updatePictureTruePictureExists(){
        Picture picture = new Picture(1, "dummylink.com/image1", false, null);
        Picture expectedResult = new Picture(1, "dummylink.com/image1", true, null);
        Mockito.when(this.pictureDao.existsById(picture.getPictureId())).thenReturn(true);
        Mockito.when(this.pictureDao.getById(picture.getPictureId())).thenReturn(picture);
        Mockito.when(this.pictureDao.save(picture)).thenReturn(expectedResult);

        Picture actualResult = this.pictureService.updatePictureTrue(picture.getPictureId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void createPictureByUserIdNoSuchUser(){
        User user1 = new User(1, "user1", "password", "email@example.com", "first", "last", null, null, null);
        Picture picture = new Picture(1, "dummylink.com/image1", false, null);
        Mockito.when(this.userDao.getById(2)).thenReturn(null);

        Picture actualResult = this.pictureService.createPictureByUserId(picture, 2);

        assertNull(actualResult);
    }

    @Test
    public void createPictureByUserSuccessful(){
        User user1 = new User(1, "user1", "password", "email@example.com", "first", "last", null, null, null);
        Picture picture = new Picture(1, "dummylink.com/image1", false, null);
        Picture expectedResult = new Picture(1, "dummylink.com/image1", false, user1);
        Mockito.when(this.userDao.getById(user1.getUserId())).thenReturn(user1);
        Mockito.when(this.pictureDao.save(picture)).thenReturn(picture);

        Picture actualResult = this.pictureService.createPictureByUserId(picture, user1.getUserId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void deletePictureInvalidPictureId(){
        Picture picture = new Picture(1, "dummylink.com/image1", false, null);
        Mockito.when(this.pictureDao.findById(picture.getPictureId())).thenReturn(Optional.ofNullable(null));

        Boolean actualResult = this.pictureService.deletePicture(picture.getPictureId());

        assertFalse(actualResult);
    }

    @Test
    public void deletePictureValidPictureId(){
        Picture picture = new Picture(1, "dummylink.com/image1", false, null);
        Mockito.when(this.pictureDao.findById(picture.getPictureId())).thenReturn(Optional.of(picture));

        Boolean actualResult = this.pictureService.deletePicture(picture.getPictureId());

        assertTrue(actualResult);
    }


    @Test
    void getAllByUserId() {
        Integer userId = 1;
        List<Picture> pictures = new ArrayList<>();

        Picture picture1 = new Picture();
        pictures.add(picture1);

        Picture picture2 = new Picture();
        pictures.add(picture2);

        Picture picture3 = new Picture();
        pictures.add(picture3);

        Mockito.when(pictureDao.findAllByUserUserId(userId)).thenReturn(pictures);

        // expected
        List<Picture> expectedResult = pictures;

        // actual
        List<Picture> actualResult = pictureService.getAllByUserId(userId);

        assertEquals(expectedResult, actualResult);
    }
}