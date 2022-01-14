package com.revature.SocialNetworkP2.controllers;

import com.revature.SocialNetworkP2.models.JsonResponse;
import com.revature.SocialNetworkP2.models.Picture;
import com.revature.SocialNetworkP2.models.User;
import com.revature.SocialNetworkP2.services.PictureService;
import com.revature.SocialNetworkP2.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PictureControllerTest {

    PictureService pictureService = Mockito.mock(PictureService.class);
    PictureController pictureController;

    public PictureControllerTest(){ this.pictureController = new PictureController(this.pictureService);}

    @Test
    void createPictureSuccess() {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        Picture picture = new Picture(1, "dummylink.com/picture", false, user);
        Picture success = new Picture(1, "dummylink.com/picture", false, user);
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.ok(new JsonResponse("picture added", picture));
        Mockito.when(this.pictureService.createOnePicture(picture)).thenReturn(success);

        ResponseEntity<JsonResponse> actualResult = this.pictureController.createPicture(picture);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createPictureFailed() {
        Picture picture = new Picture(1, "dummylink.com/picture", false, null);
        Picture failed = null;
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponse("picture not created", failed));
        Mockito.when(this.pictureService.createOnePicture(picture)).thenReturn(null);

        ResponseEntity<JsonResponse> actualResult = this.pictureController.createPicture(picture);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getAllPicturesSuccess() {
        List<Picture> pictures = new ArrayList<>();
        pictures.add(new Picture(1, "dummylink.com/picture", false, null));
        pictures.add(new Picture(2, "dummylink.com/picture2", false, null));
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.ok(new JsonResponse("successful get request", pictures));
        Mockito.when(this.pictureService.getAllPictures()).thenReturn(pictures);

        ResponseEntity<JsonResponse> actualResult = this.pictureController.getAllPictures();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getAllPicturesFailed() {
        List<Picture> pictures = null;
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponse("bad request", pictures));
        Mockito.when(this.pictureService.getAllPictures()).thenReturn(pictures);

        ResponseEntity<JsonResponse> actualResult = this.pictureController.getAllPictures();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getAllByUserIdValidId() {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        List<Picture> pictures = new ArrayList<>();
        pictures.add(new Picture(1, "dummylink.com/picture", true, user));
        pictures.add(new Picture(2, "dummylink.com/picture2", false, user));
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.ok(new JsonResponse("successful get request", pictures));
        Mockito.when(this.pictureService.getAllByUserId(user.getUserId())).thenReturn(pictures);

        ResponseEntity<JsonResponse> actualResult = this.pictureController.getAllByUserId(user.getUserId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getAllByUserIdInvalidId() {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        User wrongUser = new User(2, "user2", "password", "email@example.com", "first", "last", null, null,null);
        List<Picture> pictures = new ArrayList<>();
        pictures.add(new Picture(1, "dummylink.com/picture", true, user));
        pictures.add(new Picture(2, "dummylink.com/picture2", false, user));
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponse("bad request", null));
        Mockito.when(this.pictureService.getAllByUserId(wrongUser.getUserId())).thenReturn(null);

        ResponseEntity<JsonResponse> actualResult = this.pictureController.getAllByUserId(wrongUser.getUserId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getProfilePictureSuccess() {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        List<Picture> pictures = new ArrayList<>();
        pictures.add(new Picture(1, "dummylink.com/picture", true, user));
        pictures.add(new Picture(2, "dummylink.com/picture2", false, user));
        Picture profilePicture = new Picture(1, "dummylink.com/picture", true, user);
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.ok(new JsonResponse("successful get request", profilePicture));
        Mockito.when(this.pictureService.getAllByUserId(user.getUserId())).thenReturn(pictures);

        ResponseEntity<JsonResponse> actualResult = this.pictureController.getProfilePicture(user.getUserId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getProfilePictureInvalidId() {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        User wrongUser = new User(2, "user2", "password", "email@example.com", "first", "last", null, null,null);
        List<Picture> pictures = new ArrayList<>();
        pictures.add(new Picture(1, "dummylink.com/picture", true, user));
        pictures.add(new Picture(2, "dummylink.com/picture2", false, user));
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponse("bad request", null));
        Mockito.when(this.pictureService.getAllByUserId(wrongUser.getUserId())).thenReturn(null);

        ResponseEntity<JsonResponse> actualResult = this.pictureController.getProfilePicture(wrongUser.getUserId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void deletePictureSuccess() {
        Picture picture = new Picture(1, "dummylink.com/picture", false, null);
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.status(HttpStatus.OK).body(new JsonResponse("Picture with ID " + 1 + " was removed", null));
        Mockito.when(this.pictureService.deletePicture(picture.getPictureId())).thenReturn(true);

        ResponseEntity<JsonResponse> actualResult = this.pictureController.deletePicture(picture.getPictureId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void deletePictureFailed() {
        Picture picture = new Picture(1, "dummylink.com/picture", false, null);
        int pictureId = 2;
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponse("There is no picture with ID " + pictureId, null));
        Mockito.when(this.pictureService.deletePicture(pictureId)).thenReturn(false);

        ResponseEntity<JsonResponse> actualResult = this.pictureController.deletePicture(pictureId);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void updatePictureFalseValidId() {
        Picture picture = new Picture(1, "dummylink.com/picture", false, null);
        Picture validPicture = new Picture(1, "dummylink.com/picture", false, null);
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.ok(new JsonResponse("picture updated", validPicture));
        Mockito.when(this.pictureService.updatePictureFalse(picture.getPictureId())).thenReturn(validPicture);

        ResponseEntity<JsonResponse> actualResult = this.pictureController.updatePictureFalse(picture.getPictureId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void updatePictureFalseInvalidId(){
        Picture picture = new Picture(1, "dummylink.com/picture", false, null);
        Picture invalid = null;
        int pictureId = 2;
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new JsonResponse("picture not updated", null));
        Mockito.when(this.pictureService.updatePictureFalse(pictureId)).thenReturn(invalid);

        ResponseEntity<JsonResponse> actualResult = this.pictureController.updatePictureFalse(pictureId);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void updatePictureTrueValidId() {
        Picture picture = new Picture(1, "dummylink.com/picture", false, null);
        Picture validPicture = new Picture(1, "dummylink.com/picture", false, null);
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.ok(new JsonResponse("picture updated", validPicture));
        Mockito.when(this.pictureService.updatePictureTrue(picture.getPictureId())).thenReturn(validPicture);

        ResponseEntity<JsonResponse> actualResult = this.pictureController.updatePictureTrue(picture.getPictureId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void updatePictureTrueInvalidId() {
        Picture picture = new Picture(1, "dummylink.com/picture", false, null);
        Picture invalidPicture = null;
        int pictureId = 2;
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new JsonResponse("picture not updated", invalidPicture));
        Mockito.when(this.pictureService.updatePictureTrue(pictureId)).thenReturn(invalidPicture);

        ResponseEntity<JsonResponse> actualResult = this.pictureController.updatePictureTrue(pictureId);

        assertEquals(expectedResult, actualResult);
    }
}