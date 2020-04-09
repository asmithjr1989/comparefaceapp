package com.revature.service;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CompareFaceService {

  String bucketName = "comparefaceapp";
  String image1 = "will1.jpeg";
  String image2 = "will2.jpeg";
  String image3 = "robert1.jpeg";

  AmazonRekognition amazonRecognitionClient =
      AmazonRekognitionClientBuilder.standard().withRegion("us-east-2").build();

  public String compareSameFace(String image1, String image2) {

    String imageResultsSameFace = "";
    CompareFacesRequest compareFacesRequest =
        new CompareFacesRequest()
            .withSourceImage(
                new Image()
                    .withS3Object(new S3Object().withName(this.image1).withBucket(bucketName)))
            .withTargetImage(
                new Image()
                    .withS3Object(new S3Object().withName(this.image2).withBucket(bucketName)))
            .withSimilarityThreshold(70F);

    try {
      CompareFacesResult result = amazonRecognitionClient.compareFaces(compareFacesRequest);
      List<CompareFacesMatch> lists = result.getFaceMatches();

      System.out.println(this.image1 + " " + this.image2);

      if (!lists.isEmpty()) {
        for (CompareFacesMatch label : lists) {
          imageResultsSameFace =
              ("The faces match and the similarity percentage is:  "
                  + label.getSimilarity().toString());

          System.out.println(label.getFace() + label.getSimilarity().toString());
        }

      } else {
        imageResultsSameFace = ("The faces are not a match");
        System.out.println(imageResultsSameFace);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return imageResultsSameFace;
  }

  public String compareDiffFaces(String photo1, String photo3) {

    String imageResultsDiffFaces = "";

    CompareFacesRequest compareFacesRequest =
        new CompareFacesRequest()
            .withSourceImage(
                new Image()
                    .withS3Object(new S3Object().withName(this.image1).withBucket(bucketName)))
            .withTargetImage(
                new Image()
                    .withS3Object(new S3Object().withName(this.image3).withBucket(bucketName)))
            .withSimilarityThreshold(70f);

    try {

      CompareFacesResult result = amazonRecognitionClient.compareFaces(compareFacesRequest);
      List<CompareFacesMatch> lists = result.getFaceMatches();

      System.out.println(this.image1 + " " + this.image3);

      if (!lists.isEmpty()) {
        for (CompareFacesMatch label : lists) {

          imageResultsDiffFaces = label.getSimilarity().toString();

          System.out.println(label.getFace() + label.getSimilarity().toString());
        }
      } else {
        imageResultsDiffFaces =
            (" The faces for " + this.image1 + " and " + this.image3 + " do not match");

        System.out.println("The faces are not match");
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return imageResultsDiffFaces;
  }
}
