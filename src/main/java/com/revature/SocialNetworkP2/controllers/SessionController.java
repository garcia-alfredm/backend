package com.revature.SocialNetworkP2.controllers;

import com.revature.SocialNetworkP2.models.JsonResponse;
import com.revature.SocialNetworkP2.models.User;
import com.revature.SocialNetworkP2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "session")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class SessionController {

    private UserService userService;

    @Autowired
    public SessionController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<JsonResponse> login(HttpSession httpSession, @RequestBody User requestBody){
        User user = this.userService.validateCredentials(requestBody);

        if(user == null)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new JsonResponse("invalid username or password", null));

        httpSession.setAttribute("user-session", user.getUserId());

        return ResponseEntity.ok(new JsonResponse("login successful", user));
    }

    @GetMapping
    public ResponseEntity<JsonResponse> checkSession(HttpSession httpSession){
        Integer userId = (Integer) httpSession.getAttribute("user-session");

        if(userId == null)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new JsonResponse("no session found", null));

        User user = this.userService.getUser(userId);

        if(user == null)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new JsonResponse("no user with id: " +userId+ " found", null));

        return ResponseEntity.ok(new JsonResponse("session found", user));
    }

    @DeleteMapping
    public ResponseEntity<JsonResponse> logout(HttpSession httpSession){
        httpSession.invalidate();

        return ResponseEntity.ok(new JsonResponse("session closed", null));
    }
}
