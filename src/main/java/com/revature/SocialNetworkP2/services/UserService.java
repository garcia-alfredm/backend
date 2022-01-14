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

    /**
     * This method calls the user repository layer to retrieves all users
     * @return List<User> Returns a list of Users
     */
    public List<User> getAllUsers(){
        return this.userDao.findAll();
    }

    /**
     * This method calls the user repository lay to
     * retrieve a User by id
     * @param userId Parameter represents the user's id
     * @return User object representing the user
     */
    public User getUser(Integer userId){
        return this.userDao.findById(userId).orElse(null);
    }

    /**
     * This method calls the user repository layer to
     * retrieve the User by the username
     * @param username Parameter representing the user's username
     * @return User object representing the user
     */
    public User getByUsername(String username) {
        User user = this.userDao.findByUsername(username);

        if(user == null)
            return null;

        return user;
    }

    /**
     * This method calls the user repository layer to
     * create a new user
     * @param credentials Represents the users registration credentials
     * @return User object representing user is returned
     */
    public User createUser(User credentials) {
        User check = userDao.findByUsername(credentials.getUsername());

        if (check != null)
            return null;

        if (!verifyEmail(credentials.getEmail()))
            return null;

        credentials.setPassword(Encryptor.encrypt(credentials.getPassword()));

        return this.userDao.save(credentials);
    }

    /**
     * This method calls the user repository layer to
     * update the information of the user in the db
     * @param user Parameter represents the user
     * @return Returns a User object representing the user
     */
    public User updateUser(User user){
        if(getUser(user.getUserId()) == null)
            return null;

        if(!verifyEmail(user.getEmail()))
            return null;

        user.setPassword(Encryptor.encrypt(user.getPassword()));

        return this.userDao.save(user);
    }

    /**
     * Method in the User Service layer used to delete a user
     * @param userId Parameter represents the user's id
     * @return Returns a boolean value indicating success of failure
     */
    public Boolean deleteUser(Integer userId){
        if(getUser(userId) == null)
            return false;

        this.userDao.deleteById(userId);
        return true;
    }

    /**
     * This method resets the user's password
     * @param userId Parameter represents the user's id
     * @return Returns a User object representing the user
     */
    public User resetPassword(Integer userId){
        User user = userDao.findById(userId).orElse(null);
        if(user == null)
            return null;

        user.setPassword(RandomString.make(12)); // sets user password to randomly generated string of length 12

        return this.userDao.save(user); // return new password
    }

    /**
     * This method validates the Users credentials
     * @param user Parameter is a User object representing the user
     * @return Returns a User object if successful, null otherwise
     */
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

    /**
     * Helper method use to verify email address
     * @param email String represents the user's email
     * @return returns a boolean value indicating success, failure
     */
    public static boolean verifyEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +  //part before @
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;

        return pat.matcher(email).matches();
    }

    /**
     * Helper method used to update the resetpassword token
     * @param token the reset password token
     * @param email represents the user's email
     */
    public void updateResetPasswordToken(String token, String email) {
        User user = userDao.findByEmail(email);
        System.out.println("User service" + user);
        if (user != null) {
            user.setResetPasswordToken(token);
            userDao.save(user);
        }
    }

    /**
     * Retreives the users by their reset password token
     * @param token Represents the user's reset token
     * @return Returns the User
     */
    public User getByResetPasswordToken(String token) {
        return userDao.findByResetPasswordToken(token);
    }

    /**
     * Helper method used to update the user's password
     * @param user Parameter representing the user
     * @param newPassword Parameter represents the new password
     */
    public void updatePassword(User user, String newPassword) {
        Encryptor passwordEncryptor = new Encryptor();
        String encryptedPassword = passwordEncryptor.encrypt(newPassword);
        user.setPassword(encryptedPassword);

        user.setResetPasswordToken(null);
        System.out.println("token cleared "+user.getResetPasswordToken());
        userDao.save(user);

    }
}
