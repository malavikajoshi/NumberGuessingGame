package com.uniquehire.numberguessinggame;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.time.LocalDateTime;

@Service
public class S3LogService {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public S3LogService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void uploadGameLog(String logContent) {
        String fileName = "game-logs/log-" + System.currentTimeMillis() + ".txt";

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType("text/plain")
                .build();

        String finalLog = "Game Log Time: " + LocalDateTime.now() + "\n" + logContent;

        s3Client.putObject(request, RequestBody.fromString(finalLog));
    }
}
