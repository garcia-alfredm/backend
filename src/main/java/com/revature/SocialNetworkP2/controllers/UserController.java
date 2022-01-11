package com.revature.SocialNetworkP2.controllers;

import com.revature.SocialNetworkP2.models.Picture;
import com.revature.SocialNetworkP2.models.User;
import com.revature.SocialNetworkP2.models.JsonResponse;
import com.revature.SocialNetworkP2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<JsonResponse> getAllUsers() {
        List<User> users = this.userService.getAllUsers();

        if(users == null)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new JsonResponse("bad request", users));

        return ResponseEntity.ok(new JsonResponse("succssful get request", users));
    }

    @GetMapping(value = "{userId}")
    public ResponseEntity<JsonResponse> getUserById(@PathVariable Integer userId) {
        User user = this.userService.getUser(userId);

        if(user == null)
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(new JsonResponse("user not found", user));

        return ResponseEntity.ok( new JsonResponse("user updated", user));
    }

    @PostMapping
    public ResponseEntity<JsonResponse> createUser(@RequestBody User credentials) {
        User user = this.userService.createUser(credentials);

        if(user == null)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new JsonResponse("username is already taken or check if email is formatted correctly", user));

        return ResponseEntity.ok(new JsonResponse("user successfully created", user));
    }

    // form data from front-end
    @PutMapping
    public ResponseEntity<JsonResponse> updateUser(@RequestBody User user) {

        //System.out.println(user);
        //System.out.println(user.getUserId()+" "+user.getFirstname());
        User resultUser = this.userService.updateUser(user);

        if(resultUser == null)
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(new JsonResponse("user not found", resultUser));

        return ResponseEntity.ok(new JsonResponse("user updated", resultUser));
    }
    /*
    @PutMapping(value="changePassword")
    public ResponseEntity<JsonResponse> changePassword(@RequestBody User user){
        User resultUser = this.userService.resetPassword(user.getUserId());

        if(resultUser == null)
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(new JsonResponse("user not found", resultUser));

        return ResponseEntity.status(HttpStatus.OK).body(new JsonResponse("user updated", resultUser));

    }
     */

    @DeleteMapping
    public ResponseEntity<JsonResponse> deleteUser(@RequestParam Integer userId) {
        if(!this.userService.deleteUser(userId))
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new JsonResponse("There is no user with ID " + userId, null));

        return ResponseEntity.ok(new JsonResponse("User with ID " + userId + " was removed", null));
    }

}
