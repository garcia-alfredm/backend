package com.revature.SocialNetworkP2.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.SocialNetworkP2.models.JsonResponse;
import com.revature.SocialNetworkP2.models.Picture;
import com.revature.SocialNetworkP2.models.User;
import com.revature.SocialNetworkP2.services.PictureService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(PictureController.class)
class PictureControllerIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PictureService pictureService;

    @Test
    void createPictureSuccess() throws Exception {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        Picture body = new Picture(1, "dummylink.com/picture", false, user);
        Picture picture = new Picture(1, "dummylink.com/picture", false, user);
        JsonResponse expectedResult = new JsonResponse("picture added", picture);
        //todo figure out why it's returning null
        Mockito.when(this.pictureService.createOnePicture(body)).thenReturn(picture);

        RequestBuilder request = MockMvcRequestBuilders.post("/picture")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(body));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void createPictureFailure() throws Exception {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        Picture body = new Picture(1, "dummylink.com/picture", false, user);
        Picture picture = null;
        JsonResponse expectedResult = new JsonResponse("picture not created", picture);
        Mockito.when(this.pictureService.createOnePicture(body)).thenReturn(picture);

        RequestBuilder request = MockMvcRequestBuilders.post("/picture")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(body));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));

    }

    @Test
    void getAllPicturesSuccess() throws Exception {
        List<Picture> pictures = new ArrayList<>();
        pictures.add(new Picture(1, "dummylink.com/picture", false, null));
        pictures.add(new Picture(2, "dummylink.com/picture2", false, null));
        JsonResponse expectedResult = new JsonResponse("successful get request", pictures);
        Mockito.when(this.pictureService.getAllPictures()).thenReturn(pictures);

        mvc.perform(MockMvcRequestBuilders.get("/picture"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void getAllPicturesFailure() throws Exception {
        List<Picture> pictures = null;
        JsonResponse expectedResult = new JsonResponse("bad request", pictures);
        Mockito.when(this.pictureService.getAllPictures()).thenReturn(pictures);

        mvc.perform(MockMvcRequestBuilders.get("/picture"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void getAllByUserIdValidId() throws Exception {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        List<Picture> pictures = new ArrayList<>();
        pictures.add(new Picture(1, "dummylink.com/picture", true, user));
        pictures.add(new Picture(2, "dummylink.com/picture2", false, user));
        JsonResponse expectedResult = new JsonResponse("successful get request", pictures);
        Mockito.when(this.pictureService.getAllByUserId(1)).thenReturn(pictures);

        mvc.perform(MockMvcRequestBuilders.get("/picture/user/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void getAllByUserIdInvalidId() throws Exception {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        User wrongUser = new User(2, "user2", "password", "email@example.com", "first", "last", null, null,null);
        List<Picture> pictures = new ArrayList<>();
        pictures.add(new Picture(1, "dummylink.com/picture", true, user));
        pictures.add(new Picture(2, "dummylink.com/picture2", false, user));
        JsonResponse expectedResult = new JsonResponse("bad request", null);
        Mockito.when(this.pictureService.getAllByUserId(wrongUser.getUserId())).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.get("/picture/user/2"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void getProfilePictureSuccess() throws Exception {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        List<Picture> pictures = new ArrayList<>();
        pictures.add(new Picture(1, "dummylink.com/picture", true, user));
        pictures.add(new Picture(2, "dummylink.com/picture2", false, user));
        Picture profilePicture = new Picture(1, "dummylink.com/picture", true, user);
        JsonResponse expectedResult = new JsonResponse("successful get request", profilePicture);
        Mockito.when(this.pictureService.getAllByUserId(user.getUserId())).thenReturn(pictures);

        mvc.perform(MockMvcRequestBuilders.get("/picture/profilePic/user/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void getProfilePictureFailure() throws Exception {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        User wrongUser = new User(2, "user2", "password", "email@example.com", "first", "last", null, null,null);
        List<Picture> pictures = new ArrayList<>();
        pictures.add(new Picture(1, "dummylink.com/picture", true, user));
        pictures.add(new Picture(2, "dummylink.com/picture2", false, user));
        JsonResponse expectedResult = new JsonResponse("bad request", null);
        Mockito.when(this.pictureService.getAllByUserId(wrongUser.getUserId())).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.get("/picture/profilePic/user/2"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void deletePictureSuccess() throws Exception {
        Picture picture = new Picture(1, "dummylink.com/picture", false, null);
        JsonResponse expectedResult = new JsonResponse("Picture with ID " + 1 + " was removed", null);
        Mockito.when(this.pictureService.deletePicture(picture.getPictureId())).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.delete("/picture/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void deletePictureFailure() throws Exception {
        Picture picture = new Picture(1, "dummylink.com/picture", false, null);
        int pictureId = 2;
        JsonResponse expectedResult = new JsonResponse("There is no picture with ID " + pictureId, null);
        Mockito.when(this.pictureService.deletePicture(pictureId)).thenReturn(false);

        mvc.perform(MockMvcRequestBuilders.delete("/picture/2"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void updatePictureFalseValidId() throws Exception {
        Picture picture = new Picture(1, "dummylink.com/picture", false, null);
        Picture validPicture = new Picture(1, "dummylink.com/picture", false, null);
        JsonResponse expectedResult = new JsonResponse("picture updated", validPicture);
        Mockito.when(this.pictureService.updatePictureFalse(picture.getPictureId())).thenReturn(validPicture);

        mvc.perform(MockMvcRequestBuilders.put("/picture/false/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void updatePictureFalseInvalidId() throws Exception {
        Picture picture = new Picture(1, "dummylink.com/picture", false, null);
        Picture invalid = null;
        int pictureId = 2;
        JsonResponse expectedResult = new JsonResponse("picture not updated", null);
        Mockito.when(this.pictureService.updatePictureFalse(pictureId)).thenReturn(invalid);

        mvc.perform(MockMvcRequestBuilders.put("/picture/false/2"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));

    }

    @Test
    void updatePictureTrueValidId() throws Exception {
        Picture picture = new Picture(1, "dummylink.com/picture", false, null);
        Picture validPicture = new Picture(1, "dummylink.com/picture", false, null);
        JsonResponse expectedResult = new JsonResponse("picture updated", validPicture);
        Mockito.when(this.pictureService.updatePictureTrue(picture.getPictureId())).thenReturn(validPicture);

        mvc.perform(MockMvcRequestBuilders.put("/picture/true/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void updatePictureTrueInvalidId() throws Exception {
        Picture picture = new Picture(1, "dummylink.com/picture", false, null);
        Picture invalidPicture = null;
        int pictureId = 2;
        JsonResponse expectedResult = new JsonResponse("picture not updated", invalidPicture);
        Mockito.when(this.pictureService.updatePictureTrue(pictureId)).thenReturn(invalidPicture);

        mvc.perform(MockMvcRequestBuilders.put("/picture/true/2"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));

    }
}