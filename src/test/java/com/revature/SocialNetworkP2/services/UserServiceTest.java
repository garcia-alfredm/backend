package com.revature.SocialNetworkP2.services;

import com.revature.SocialNetworkP2.models.User;
import com.revature.SocialNetworkP2.repository.UserDao;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    UserDao userDao = Mockito.mock(UserDao.class);
    UserService userService;

    public UserServiceTest() { this.userService = new UserService(userDao); }

    @Test
    void getByUsername(){
        User user1 = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        User expectedResult = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);

        Mockito.when(userDao.findByUsername(user1.getUsername())).thenReturn(expectedResult);

        User actualResult = userService.getByUsername(user1.getUsername());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getByUsernameInvalid(){
        User user1 = new User(1, "incorrect", "password", "email@example.com", "first", "last", null, null,null);

        Mockito.when(userDao.findByUsername(user1.getUsername())).thenReturn(null);

        User result = userService.getByUsername(user1.getUsername());

        assertNull(result);
    }

    @Test
    void createUserWhenUsernameTaken(){
        //assign
        User user1 = new User(1, "incorrect", "password", "email@example.com", "first", "last", null, null, null);
        Mockito.when(userDao.findByUsername(user1.getUsername())).thenReturn(null);

        //act
        User actualResults  = this.userService.createUser(user1);

        //assert
        assertNull(actualResults);
    }

    @Test
    void createUserIncorrectEmailFormat(){
        //assign
        User user1 = new User(1, "user1", "password", "emailexample.com", "first", "last", null, null, null);
        Mockito.when(userDao.findByUsername(user1.getUsername())).thenReturn(null);

        //act
        User actualResults  = this.userService.createUser(user1);

        //assert
        assertNull(actualResults);
    }

    @Test
    void createUserSuccessful(){
        //assign
        User expectedResult = new User(1, "user1", "password", "email@example.com", "first", "last", null, null, null);
        Mockito.when(this.userDao.findByUsername(expectedResult.getUsername())).thenReturn(null);
        Mockito.when(this.userDao.save(expectedResult)).thenReturn(expectedResult);

        //act
        User actualResults = this.userService.createUser(expectedResult);

        //assert
        assertEquals(expectedResult, actualResults);
    }

    @Test
    public void validateCredentialsValid() {
        User user1 = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        User user2 = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        Mockito.when(this.userDao.findByUsername(user1.getUsername())).thenReturn(user2);

        User actualResult = this.userService.validateCredentials(user1);

        assertEquals(user1, actualResult);
    }

    @Test
    public void validateCredentialsInvalidUsername() {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);

        Mockito.when(this.userDao.findByUsername(user.getUsername())).thenReturn(null);

        User result = this.userService.validateCredentials(user);

        assertNull(result);
    }

    @Test
    public void validateCredentialsIncorrectPassword() {
        User user1 = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        User user2 = new User(1, "user1", "password123", "email@example.com", "first", "last", null, null,null);

        Mockito.when(this.userDao.findByUsername(user1.getUsername())).thenReturn(user2);

        User result = this.userService.validateCredentials(user1);

        assertNull(result);
    }

    @Test
    void verifyEmail(){
        String email = "example@gmail.com";
        assertTrue(UserService.verifyEmail(email));
    }
    @Test
    void verifyEmailInvalidEmail(){
        String email = "examplegmail.com";
        assertFalse(UserService.verifyEmail(email));
    }
    @Test
    void verifyEmailInvalidCharacters(){
        String email = "exam%%*ple@gma&il.com";
        assertFalse(UserService.verifyEmail(email));
    }

    @Test
    public void updateUserNoSuchUser(){
        //assign
        User user1 = new User(1, "user1", "password", "email@example.com", "first", "last", null, null, null);
        User expectedResult = null;
        Mockito.when(this.userDao.findById(user1.getUserId())).thenReturn(java.util.Optional.ofNullable(null));

        //act
        User actualResult = this.userService.updateUser(user1);

        //assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void updateUserWrongEmailFormat(){
        //assign
        User user1 = new User(1, "user1", "password", "emailexample.com", "first", "last", null, null, null);
        User expectedResult = null;
        Mockito.when(this.userDao.findById(user1.getUserId())).thenReturn(java.util.Optional.of(user1));

        //act
        User actualResult = this.userService.updateUser(user1);

        //assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void updateUserSuccess(){
        //assign
        User expectedResult = new User(1, "user1", "password", "email@example.com", "first", "last", null, null, null);
        Mockito.when(this.userDao.findById(expectedResult.getUserId())).thenReturn(java.util.Optional.of(expectedResult));
        Mockito.when(this.userDao.save(expectedResult)).thenReturn(expectedResult);
        //act
        User actualResult = this.userService.updateUser(expectedResult);

        //assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void deleteUserNoSuchUser(){
        //assign
        User user1 = new User(1, "user1", "password", "email@example.com", "first", "last", null, null, null);
        Boolean expectedResult = false;
        Mockito.when(this.userDao.findById(user1.getUserId())).thenReturn(java.util.Optional.ofNullable(null));

        //act
        Boolean actualResult = this.userService.deleteUser(user1.getUserId());

        //assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void deleteUserSucessful(){
        //assign
        User user1 = new User(1, "user1", "password", "email@example.com", "first", "last", null, null, null);
        Boolean expectedResult = true;
        Mockito.when(this.userDao.findById(user1.getUserId())).thenReturn(java.util.Optional.of(user1));

        //act
        Boolean actualResult = this.userService.deleteUser(user1.getUserId());

        //assert
        assertEquals(expectedResult, actualResult);
        Mockito.verify(this.userDao, Mockito.times(1)).deleteById(user1.getUserId());
    }

    @Test
    public void resetPassword() {
        User user = new User(1, "user1", "password", "email@example.com", "first", "last", null, null,null);
        Mockito.when(this.userDao.findById(user.getUserId())).thenReturn(java.util.Optional.of(user));
        this.userService.resetPassword(user.getUserId());
        Mockito.verify(this.userDao, Mockito.times(1)).save(user);
    }

    @Test
    public void resetPasswordInvalid() {
        User user = new User(99, "user1", "password", "email@example.com", "first", "last", null, null,null);
        Mockito.when(this.userDao.findById(user.getUserId())).thenReturn(java.util.Optional.ofNullable(null));
        userService.resetPassword(user.getUserId());
        Mockito.verify(this.userDao, Mockito.times(0)).save(user);
    }
}