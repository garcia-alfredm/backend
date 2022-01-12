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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(UserController.class)
class UserControllerIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    void getAllUsersNoUsers() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null));
        users.add(new User(2, "user2", "password", "email@example.com", "first", "last", null, null,null));
        JsonResponse expectedResult = new JsonResponse("bad request", null);
        Mockito.when(this.userService.getAllUsers()).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.get("/user"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void getAllUsersSuccessful() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null));
        users.add(new User(2, "user2", "password", "email@example.com", "first", "last", null, null,null));
        JsonResponse expectedResult = new JsonResponse("succssful get request", users);
        Mockito.when(this.userService.getAllUsers()).thenReturn(users);

        mvc.perform(MockMvcRequestBuilders.get("/user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void getUserByIdInvalidId() throws Exception {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        JsonResponse expectedResult = new JsonResponse("user not found", null);
        Mockito.when(this.userService.getUser(2)).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.get("/user/2")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));

    }

    @Test
    void getUserByIdValidId() throws Exception {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        JsonResponse expectedResult = new JsonResponse("user updated", user);
        Mockito.when(this.userService.getUser(user.getUserId())).thenReturn(user);

        RequestBuilder request = MockMvcRequestBuilders.get("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void createUserFailed() throws Exception {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        JsonResponse expectedResult = new JsonResponse("username is already taken or check if email is formatted correctly", null);
        Mockito.when(this.userService.createUser(user)).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void createUserSuccessful() throws Exception {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        JsonResponse expectedResult = new JsonResponse("user successfully created", user);
        Mockito.when(this.userService.createUser(user)).thenReturn(user);

        RequestBuilder request = MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void updateUserFailed() throws Exception {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        User account = null;
        JsonResponse expectedResult = new JsonResponse("user not found", account);
        Mockito.when(this.userService.updateUser(user)).thenReturn(account);

        mvc.perform(MockMvcRequestBuilders.put("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void updateUserSuccessful() throws Exception {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        User account = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);;
        JsonResponse expectedResult = new JsonResponse("user updated", account);
        Mockito.when(this.userService.updateUser(user)).thenReturn(account);

        RequestBuilder request = MockMvcRequestBuilders.put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void deleteUserInvalidId() throws Exception {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        Integer userId = 2;
        JsonResponse expectedResult = new JsonResponse("There is no user with ID " + userId, null);
        Mockito.when(this.userService.deleteUser(userId)).thenReturn(false);

        mvc.perform(MockMvcRequestBuilders.delete("/user").param("userId", "2"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void deleteUserSuccessful() throws Exception {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        Integer userId = user.getUserId();
        JsonResponse expectedResult = new JsonResponse("User with ID " + userId + " was removed", null);
        Mockito.when(this.userService.deleteUser(userId)).thenReturn(true);

        RequestBuilder request = MockMvcRequestBuilders.delete("/user").param("userId", "1");

        mvc.perform(request).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }
}