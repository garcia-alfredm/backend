package com.revature.SocialNetworkP2.controllers;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.revature.SocialNetworkP2.models.Picture;
import com.revature.SocialNetworkP2.models.JsonResponse;
import com.revature.SocialNetworkP2.models.User;
import com.revature.SocialNetworkP2.services.PictureService;
import com.revature.SocialNetworkP2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "picture")
@CrossOrigin(origins = "http://localhost:4200",allowCredentials = "true")
//@CrossOrigin(origins = "*",allowCredentials = "true")
public class PictureController {

    private PictureService pictureService;
    private UserService userService;

    private final String awsID = "AKIA24ZT7ASW4LRLBMFF";
    private final String secretKet = "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";
    private final String region="us-east-2";
    private final String bucketName = "mmwm-jwa-s3";

    BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsID,secretKet);
    AmazonS3 s3Client = AmazonS3ClientBuilder
            .standard()
            .withRegion(Regions.fromName(region))
            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
            .build();

    @Autowired
    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @PostMapping
    public ResponseEntity<JsonResponse> createPicture(@RequestBody Picture body){
        Picture picture = this.pictureService.createOnePicture(body);

        if(picture == null)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new JsonResponse("picture not created", picture));

        return ResponseEntity.ok(new JsonResponse("picture added", picture));
    }
    @PostMapping(value="{userId}/file")
    public ResponseEntity<JsonResponse> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Integer userId) throws IOException {

        Picture pic=new Picture();
        User user = new User();
        user = this.pictureService.getByUserId(userId);

        ObjectMetadata data = new ObjectMetadata();

        System.out.println(file.getOriginalFilename());
        System.out.println(userId);

        s3Client.putObject(bucketName, file.getOriginalFilename(), file.getInputStream(), data);
        pic.setPictureLink("https://mmwm-jwa-s3.s3.us-east-2.amazonaws.com/"+file.getOriginalFilename());
        pic.setUser(user);
        pic.setProfilePicture(false);

        pic = this.pictureService.createPictureByUserId(pic,userId);
        System.out.println(pic);
        System.out.println(pic.getPictureLink());
        //return ResponseEntity.ok(new JsonResponse( "Uploaded!" ,pic.getPictureId()));
        return ResponseEntity.ok(new JsonResponse( "Uploaded!" ,pic.getPictureLink()));
    }

    @GetMapping
    public ResponseEntity<JsonResponse> getAllPictures() {
        List<Picture> pictures = this.pictureService.getAllPictures();

        if(pictures == null)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new JsonResponse("bad request", pictures));

        return ResponseEntity.ok(new JsonResponse("successful get request", pictures));
    }

    @GetMapping(value = "{pictureId}")
    public ResponseEntity<JsonResponse> getPicture(@PathVariable Integer pictureId) {
        Picture picture = this.pictureService.getOnePicture(pictureId);

        if(picture == null)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new JsonResponse("bad request", picture));

        return ResponseEntity.ok(new JsonResponse("successful get request", picture));
    }

    // returns all pictures user has uploaded
    @GetMapping(value = "user/{userId}")
    public ResponseEntity<JsonResponse> getAllByUserId(@PathVariable Integer userId) {
        List<Picture> pictures = this.pictureService.getAllByUserId(userId);

        if(pictures == null)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new JsonResponse("bad request", pictures));

        return ResponseEntity.ok(new JsonResponse("successful get request", pictures));
    }
    @GetMapping(value = "profilePic/user/{userId}")
    public ResponseEntity<JsonResponse> getProfilePicture(@PathVariable Integer userId) {
        List<Picture> pictures = this.pictureService.getAllByUserId(userId);

        Picture picture = new Picture(), profile_picture = new Picture();
        for(int i=0;i<pictures.size();i++)
        {
            picture = pictures.get(i);
            if(picture.getProfilePicture())
                profile_picture = picture;
        }

        if(pictures == null)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new JsonResponse("bad request", pictures));

        return ResponseEntity.ok(new JsonResponse("successful get request", profile_picture));
    }

    @DeleteMapping(value = "{pictureId}")
    //public ResponseEntity<JsonResponse> deletePicture(@RequestParam Integer pictureId) {
        public ResponseEntity<JsonResponse> deletePicture(@PathVariable Integer pictureId) {
        if(!this.pictureService.deletePicture(pictureId))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponse("There is no picture with ID " + pictureId, null));

        return ResponseEntity.status(HttpStatus.OK).body(new JsonResponse("Picture with ID " + pictureId + " was removed", null));
    }

    @PutMapping(value = "false/{pictureId}")
    public ResponseEntity<JsonResponse> updatePictureFalse(@PathVariable Integer pictureId){
        Picture picture = this.pictureService.updatePictureFalse(pictureId);

        if(picture == null)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new JsonResponse("picture not updated", picture));

        return ResponseEntity.ok(new JsonResponse("picture updated", picture));
    }
    @PutMapping(value = "true/{pictureId}")
    public ResponseEntity<JsonResponse> updatePictureTrue(@PathVariable Integer pictureId){
        Picture picture = this.pictureService.updatePictureTrue(pictureId);

        if(picture == null)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new JsonResponse("picture not updated", picture));

        return ResponseEntity.ok(new JsonResponse("picture updated", picture));
    }
}
