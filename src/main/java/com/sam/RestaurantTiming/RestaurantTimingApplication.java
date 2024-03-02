package com.sam.RestaurantTiming;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@SpringBootApplication
public class RestaurantTimingApplication {
	public static void main(String[] args) throws JsonProcessingException {
		SpringApplication.run(RestaurantTimingApplication.class, args);
	}
}
