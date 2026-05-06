package com.uniquehire.numberguessinggame;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@SpringBootApplication
public class NumberGuessingGameApplication {

    @Value("${aws.region}")
    private String awsRegion;

    public static void main(String[] args) {
        SpringApplication.run(NumberGuessingGameApplication.class, args);
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(awsRegion))
                .build();
    }
}