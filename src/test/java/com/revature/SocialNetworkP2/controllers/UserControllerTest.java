package com.revature.SocialNetworkP2.controllers;

import com.revature.SocialNetworkP2.models.JsonResponse;
import com.revature.SocialNetworkP2.models.User;
import com.revature.SocialNetworkP2.services.UserService;
import org.apache.coyote.Response;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    UserService userService = Mockito.mock(UserService.class);
    UserController userController;

    public UserControllerTest() {
        this.userController = new UserController(this.userService);
    }

    @Test
    void getAllUsersNoUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null));
        users.add(new User(2, "user2", "password", "email@example.com", "first", "last", null, null,null));
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponse("bad request", null));
        Mockito.when(this.userService.getAllUsers()).thenReturn(null);

        ResponseEntity<JsonResponse> actualResult = this.userController.getAllUsers();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getAllUsersSuccessful() {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null));
        users.add(new User(2, "user2", "password", "email@example.com", "first", "last", null, null,null));
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.ok(new JsonResponse("succssful get request", users));
        Mockito.when(this.userService.getAllUsers()).thenReturn(users);

        ResponseEntity<JsonResponse> actualResult = this.userController.getAllUsers();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getUserByIdInvalidId() {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.status(HttpStatus.NO_CONTENT).body(new JsonResponse("user not found", null));
        Mockito.when(this.userService.getUser(2)).thenReturn(null);

        ResponseEntity<JsonResponse> actualResult = this.userController.getUserById(2);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getUserByIdValidId() {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.ok(new JsonResponse("user updated", user));
        Mockito.when(this.userService.getUser(user.getUserId())).thenReturn(user);

        ResponseEntity<JsonResponse> actualResult = this.userController.getUserById(user.getUserId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createUserFailed() {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponse("username is already taken or check if email is formatted correctly", null));
        Mockito.when(this.userService.createUser(user)).thenReturn(null);

        ResponseEntity<JsonResponse> actualResult = this.userController.createUser(user);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createUserSuccessful() {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.ok(new JsonResponse("user successfully created", user));
        Mockito.when(this.userService.createUser(user)).thenReturn(user);

        ResponseEntity<JsonResponse> actualResult = this.userController.createUser(user);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void updateUserFailed() {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        User account = null;
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.status(HttpStatus.NO_CONTENT).body(new JsonResponse("user not found", account));
        Mockito.when(this.userService.updateUser(user)).thenReturn(account);

        ResponseEntity<JsonResponse> actualResult = this.userController.updateUser(user);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void updateUserSuccessful() {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        User account = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);;
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.ok(new JsonResponse("user updated", account));
        Mockito.when(this.userService.updateUser(user)).thenReturn(account);

        ResponseEntity<JsonResponse> actualResult = this.userController.updateUser(user);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void deleteUserInvalidId() {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        Integer userId = 2;
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponse("There is no user with ID " + userId, null));
        Mockito.when(this.userService.deleteUser(userId)).thenReturn(false);

        ResponseEntity<JsonResponse> actualResult = this.userController.deleteUser(userId);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void deleteUserValidId() {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        Integer userId = user.getUserId();
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.ok(new JsonResponse("User with ID " + userId + " was removed", null));
        Mockito.when(this.userService.deleteUser(userId)).thenReturn(true);

        ResponseEntity<JsonResponse> actualResult = this.userController.deleteUser(userId);

        assertEquals(expectedResult, actualResult);
    }
}