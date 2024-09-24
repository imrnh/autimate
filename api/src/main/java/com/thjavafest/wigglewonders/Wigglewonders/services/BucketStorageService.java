package com.thjavafest.wigglewonders.Wigglewonders.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;


import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class BucketStorageService {

    final String bucketName = "wiggle-space";
    final Duration urlValPeriod = Duration.ofMinutes(30);
    final URI serverEndpoint = URI.create("https://nyc3.digitaloceanspaces.com");
    final String accessKey = "DO00ND8KPADVKJDN6Z8V";
    final String secretKey = "t4KrqnMCaQuzgwFwNzgjyxFk2wDq9Bkx/doXKioSyM4";

    public String getPreSignedUrl(){
        String objectKey = UUID.randomUUID().toString().replace("-", "").substring(0, 20) + ".mp4";  // Generate a random name of 20 characters for the file.

        try{
            AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);

            // Generate pre-signed URL. Set expiration to 30 minutes.
            S3Presigner preSignedUrlGenerator = S3Presigner.builder()
                    .region(Region.US_EAST_1).credentialsProvider(StaticCredentialsProvider.create(awsCredentials)).endpointOverride(serverEndpoint).build();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(objectKey).build();
            PutObjectPresignRequest preSignRequest = PutObjectPresignRequest.builder()
                    .putObjectRequest(putObjectRequest)
                    .signatureDuration(urlValPeriod)
                    .build();
            URL presignedUrl = preSignedUrlGenerator.presignPutObject(preSignRequest).url();

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> response = new HashMap<>();
            response.put("presignedUrl", presignedUrl.toString());
            response.put("uuid", objectKey.toString());

            //Convert the response map to JSON
            preSignedUrlGenerator.close();
            return objectMapper.writeValueAsString(response); // Return JSON string
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

}
