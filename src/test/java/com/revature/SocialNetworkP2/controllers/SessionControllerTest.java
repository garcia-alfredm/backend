package com.revature.SocialNetworkP2.controllers;

import com.revature.SocialNetworkP2.models.JsonResponse;
import com.revature.SocialNetworkP2.models.User;
import com.revature.SocialNetworkP2.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpSession;
import javax.swing.text.html.parser.Entity;

import static org.junit.jupiter.api.Assertions.*;

class SessionControllerTest {

    UserService userService = Mockito.mock(UserService.class);
    private HttpSession session = Mockito.mock(HttpSession.class);

    SessionController sessionController;

    public SessionControllerTest(){

        this.sessionController = new SessionController(this.userService);
    }

    @Test
    void loginInvalidUsernamePassword() {
        User requestBody = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        User invalid = null;
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponse("invalid username or password", null));
        Mockito.when(this.userService.validateCredentials(requestBody)).thenReturn(invalid);

        ResponseEntity<JsonResponse> actualResult = this.sessionController.login(this.session, requestBody);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void loginValidUsernamePassword() {
        User requestBody = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        User validUser = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.ok(new JsonResponse("login successful", validUser));
        Mockito.when(this.userService.validateCredentials(requestBody)).thenReturn(validUser);

        ResponseEntity<JsonResponse> actualResult = this.sessionController.login(this.session, requestBody);

        assertEquals(expectedResult, actualResult);
        Mockito.verify(this.session, Mockito.times(1)).setAttribute("user-session", validUser.getUserId());
    }

    @Test
    void checkSessionNoSessionFound() {
        User requestBody = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        User noActiveUser = null;
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponse("no session found", null));
        Mockito.when(this.session.getAttribute("user-session")).thenReturn(noActiveUser);

        ResponseEntity<JsonResponse> actualResult = this.sessionController.checkSession(this.session);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void checkSessionIncorrectUserId() {
        User activeUser = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        Integer userId = 2;
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponse("no user with id: " +userId+ " found", null));
        Mockito.when(this.session.getAttribute("user-session")).thenReturn(userId);
        Mockito.when(this.userService.getUser(activeUser.getUserId())).thenReturn(null);

        ResponseEntity<JsonResponse> actualResult = this.sessionController.checkSession(this.session);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void checkSessionActiveSession() {
        User activeUser = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        User success = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        Integer userId = activeUser.getUserId();
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.ok(new JsonResponse("session found", success));
        Mockito.when(this.session.getAttribute("user-session")).thenReturn(userId);
        Mockito.when(this.userService.getUser(activeUser.getUserId())).thenReturn(success);

        ResponseEntity<JsonResponse> actualResult = this.sessionController.checkSession(this.session);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void logout() {
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.ok(new JsonResponse("session closed", null));

        ResponseEntity<JsonResponse> actualResult = this.sessionController.logout(this.session);

        assertEquals(expectedResult, actualResult);
        Mockito.verify(this.session, Mockito.times(1)).invalidate();
    }
}