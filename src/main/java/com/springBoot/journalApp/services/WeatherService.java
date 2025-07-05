package com.springBoot.journalApp.services;

import com.springBoot.journalApp.apiResponse.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    private static final String apiKey = "585f423f14d77ebff624bb12a2ac7ffb";
    private static final String api = "https://api.weatherstack.com/current?access_key=api_key&query=City";

    @Autowired
    private RestTemplate restTemplate;
    public WeatherResponse getWeather(String city) {
            String finalApi = api.replace("City",city).replace("api_key",apiKey);
            ResponseEntity<WeatherResponse> response  = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            return body;
    }
}
