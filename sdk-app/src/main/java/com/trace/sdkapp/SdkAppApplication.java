package com.trace.sdkapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class SdkAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SdkAppApplication.class, args);
	}

}
