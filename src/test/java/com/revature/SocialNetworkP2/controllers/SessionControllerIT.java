package com.revature.SocialNetworkP2.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.SocialNetworkP2.models.JsonResponse;
import com.revature.SocialNetworkP2.models.User;
import com.revature.SocialNetworkP2.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.mail.Session;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(SessionController.class)
class SessionControllerIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private MockHttpSession session;

    @Test
    void loginValidUsernamePassword() throws Exception {
        User requestBody = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        User validUser = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        JsonResponse expectedResult = new JsonResponse("login successful", validUser);
        Mockito.when(this.userService.validateCredentials(requestBody)).thenReturn(validUser);

        RequestBuilder request = MockMvcRequestBuilders.post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestBody));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void loginInvalidUsernamePassword() throws Exception {
        User requestBody = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        User invalid = null;
        JsonResponse expectedResult = new JsonResponse("invalid username or password", invalid);
        Mockito.when(this.userService.validateCredentials(requestBody)).thenReturn(invalid);

        mvc.perform(MockMvcRequestBuilders.post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestBody)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void checkSessionValidSession() throws Exception {
        User activeUser = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        User success = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        Integer userId = activeUser.getUserId();
        JsonResponse expectedResult = new JsonResponse("session found", success);
        Mockito.when(this.session.getAttribute("user-session")).thenReturn(userId);
        Mockito.when(this.userService.getUser(activeUser.getUserId())).thenReturn(success);

        mvc.perform(MockMvcRequestBuilders.get("/session").session(this.session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void checkSessionSessionNotFound() throws Exception {
        User noActiveUser = null;
        JsonResponse expectedResult = new JsonResponse("no session found", null);
        Mockito.when(this.session.getAttribute("user-session")).thenReturn(noActiveUser);

        mvc.perform(MockMvcRequestBuilders.get("/session").session(this.session))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void checkSessionInvalidUserId() throws Exception {
        User activeUser = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        Integer userId = 2;
        JsonResponse expectedResult = new JsonResponse("no user with id: " +userId+ " found", null);
        Mockito.when(this.session.getAttribute("user-session")).thenReturn(userId);
        Mockito.when(this.userService.getUser(activeUser.getUserId())).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.get("/session").session(this.session))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void logout() throws Exception {
        JsonResponse expectedResult = new JsonResponse("session closed", null);

        mvc.perform(MockMvcRequestBuilders.delete("/session").session(this.session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }
}