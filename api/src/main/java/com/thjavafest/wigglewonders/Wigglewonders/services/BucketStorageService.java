package com.thjavafest.wigglewonders.Wigglewonders.services;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.net.URL;
import java.time.Duration;
import java.net.URI;
import java.util.UUID;

@Service
public class BucketStorageService {
    final String bucketName = "ww_s3";

    private String uniqueString(int length){
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid.substring(0, Math.min(length, uuid.length()));
    }

    public String getPreSignedUrl(){
        String objectKey = uniqueString(15) + ".mp4";
        S3Presigner presigner = S3Presigner.builder()
                .region(Region.US_EAST_1) // Specify your region
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();

        // Create a GetObjectRequest for the specific object
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(getObjectRequest)
                .signatureDuration(Duration.ofMinutes(10)) // Set the URL expiration time
                .build();

        URL presignedUrl = presigner.presignGetObject(presignRequest).url();

        return presignedUrl.toString();
    }
}
