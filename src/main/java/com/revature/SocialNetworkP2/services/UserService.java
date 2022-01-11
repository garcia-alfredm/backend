package com.revature.SocialNetworkP2.services;

import com.revature.SocialNetworkP2.models.Encryptor;
import com.revature.SocialNetworkP2.models.User;
import com.revature.SocialNetworkP2.repository.UserDao;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserService {

    private UserDao userDao;

    /***************************   CONSTRUCTOR   ***************************/

    @Autowired
    public UserService(UserDao userDao){
        this.userDao = userDao;
    }

    /******************************   METHODS   ******************************/

    public List<User> getAllUsers(){
        return this.userDao.findAll();
    }

    public User getUser(Integer userId){
        return this.userDao.findById(userId).orElse(null);
    }

    public User getByUsername(String username) {
        User user = this.userDao.findByUsername(username);

        if(user == null)
            return null;

        return user;
    }

    public User createUser(User credentials) {
        User check = userDao.findByUsername(credentials.getUsername());

        if (check != null)
            return null;

        if (!verifyEmail(credentials.getEmail()))
            return null;

        credentials.setPassword(Encryptor.encrypt(credentials.getPassword()));

        return this.userDao.save(credentials);
    }

    public User updateUser(User user){
        if(getUser(user.getUserId()) == null)
            return null;

        if(!verifyEmail(user.getEmail()))
            return null;

        user.setPassword(Encryptor.encrypt(user.getPassword()));

        return this.userDao.save(user);
    }

    public Boolean deleteUser(Integer userId){
        if(getUser(userId) == null)
            return false;

        this.userDao.deleteById(userId);
        return true;
    }

    public User resetPassword(Integer userId){
        User user = userDao.findById(userId).orElse(null);
        if(user == null)
            return null;

        user.setPassword(RandomString.make(12)); // sets user password to randomly generated string of length 12

        return this.userDao.save(user); // return new password
    }

    public User validateCredentials(User user){
        User userFromDb = getByUsername(user.getUsername());

        if(userFromDb == null)
            return null;

        if(!Encryptor.decrypt(userFromDb.getPassword()).equals(user.getPassword()))
            return null;

        return userFromDb;
    }

    /******************************   HELPER METHODS   ******************************/

    // may not need this depending on how we develop our front-end, so it is not yet implemented in our current service methods
    public static boolean verifyEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +  //part before @
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;

        return pat.matcher(email).matches();
    }
    public void updateResetPasswordToken(String token, String email) {
        User user = userDao.findByEmail(email);
        System.out.println("User service" + user);
        if (user != null) {
            user.setResetPasswordToken(token);
            userDao.save(user);
        }
    }

    public User getByResetPasswordToken(String token) {
        return userDao.findByResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword) {
        Encryptor passwordEncryptor = new Encryptor();
        String encryptedPassword = passwordEncryptor.encrypt(newPassword);
        user.setPassword(encryptedPassword);

        user.setResetPasswordToken(null);
        System.out.println("token cleared "+user.getResetPasswordToken());
        userDao.save(user);

    }
}
