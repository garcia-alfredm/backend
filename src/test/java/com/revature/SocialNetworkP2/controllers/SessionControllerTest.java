package com.revature.SocialNetworkP2.controllers;

import com.revature.SocialNetworkP2.models.JsonResponse;
import com.revature.SocialNetworkP2.models.User;
import com.revature.SocialNetworkP2.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;

import static org.junit.jupiter.api.Assertions.*;

class SessionControllerTest {

    UserService userService = Mockito.mock(UserService.class);
    SessionController sessionController;
    @MockBean
    private MockHttpSession session;

    public SessionControllerTest(){
        this.sessionController = new SessionController(this.userService);
    }

    @Test
    void loginInvalidUsernamePassword() {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponse("invalid username or password", null));
        Mockito.when(this.userService.validateCredentials(user)).thenReturn(null);


    }

    @Test
    void loginValidUsernamePassword() {
    }

    @Test
    void checkSession() {
    }

    @Test
    void logout() {
    }
}