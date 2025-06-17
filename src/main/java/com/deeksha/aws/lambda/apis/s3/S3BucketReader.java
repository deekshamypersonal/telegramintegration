package com.deeksha.aws.lambda.apis.s3;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.util.Scanner;

public class S3BucketReader {

   // Values set from environment variables
    static String bucketName;
    static String key; 


    public static String getToken() {

        S3Client s3 = S3Client.builder()
                .region(Region.of("us-east-1")) 
                .build();

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        ResponseInputStream<GetObjectResponse> s3is = s3.getObject(getObjectRequest);
        Scanner scanner = new Scanner(s3is).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";

    }
}
