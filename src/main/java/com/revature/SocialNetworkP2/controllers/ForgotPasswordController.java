

package com.revature.SocialNetworkP2.controllers;

import com.revature.SocialNetworkP2.models.JsonResponse;
import com.revature.SocialNetworkP2.models.User;
import com.revature.SocialNetworkP2.services.UserService;
import net.bytebuddy.utility.RandomString;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping
@CrossOrigin (origins = "http://localhost:4200")
public class ForgotPasswordController {
    private Logger logger = Logger.getLogger(ForgotPasswordController.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;
    /**
     * This method is used to generate a new reset password token.
     * @param request takes the current post request and uses the body as the User model
     * @return ResponseEntity<JsonResponse> this returns the status of the JsonResponse
     */
    @PostMapping(value = "forgotPassword")
    public ResponseEntity<JsonResponse> processForgotPassword(@RequestBody User request) {
        String email = request.getEmail();
        String token = RandomString.make(30);

        try {
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = "http://localhost:4200/resetPassword";

            sendEmail(email, resetPasswordLink, token);

        } catch (UnsupportedEncodingException | MessagingException e) {
            ResponseEntity.noContent();
        } catch ( Exception e) {
            logger.warn(e);
        }

        return ResponseEntity.ok(new JsonResponse("email successfully created", email));
    }

    /**
     * This method is used to send Emails
     * @param email This param Takes user email from post request
     * @param resetPasswordLink This param takes in the resetPassword Link
     * @param token This param takes the randomly generated token
     * @throws MessagingException for messaging errors
     * @throws UnsupportedEncodingException for unsupported encoding error
     */
    public void sendEmail (String email, String resetPasswordLink, String token) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("project2teamair@gmail.com", "Airie");
        helper.setTo(email);

        String subject = "Here is the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Copy your Password Token below. Use it to change your password.</p>"
                + "<p>" + token +"</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + resetPasswordLink + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

    /**
     * This method completes the password reset
     * @param request takes the current post request and uses the body as the User model
     * @return ResponseEntity<JsonResponse> this returns the status of the JsonResponse
     */
    @PostMapping(value = "resetPassword")
    public ResponseEntity<JsonResponse> processResetPassword(@RequestBody User request) {
        String token = request.getResetPasswordToken();
        String password = request.getPassword();

        User user = userService.getByResetPasswordToken(token);
        //System.out.println("found user"+user);

        if (user == null) {

            return ResponseEntity.ok(new JsonResponse("reset successful", token));
        } else {
            userService.updatePassword(user, password);
        }

        return ResponseEntity.ok(new JsonResponse("reset successful", token));
    }
}