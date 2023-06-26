package com.weatherapp.myweatherapp.controller;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException;

@Controller
public class WeatherController {

  @Autowired
  WeatherService weatherService;

  @GetMapping("/forecast/")
  public ResponseEntity<String> forecastWithoutCity() {
      return new ResponseEntity<>("City name is required", HttpStatus.BAD_REQUEST);
  }

  @GetMapping("/forecast/{city}")
  public ResponseEntity<?> forecastByCity(@PathVariable("city") String city) {
    try {
      CityInfo ci = weatherService.forecastByCity(city);
      return ResponseEntity.ok(ci);
    } catch (HttpClientErrorException.BadRequest e) {
      return new ResponseEntity<>("City not found: " + city, HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/daylight/{city1}/{city2}")
  public ResponseEntity<String> compareDaylightHours(@PathVariable String city1, @PathVariable String city2) {
    try {
      String result = weatherService.compareDaylightHours(city1, city2);
      return ResponseEntity.ok(result);
    } catch (HttpClientErrorException.BadRequest e) {
      return new ResponseEntity<>("One or both cities not found", HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/raincheck/{city1}/{city2}")
  public ResponseEntity<String> checkRain(@PathVariable String city1, @PathVariable String city2) {
    try {
      String result = weatherService.checkRain(city1, city2);
      return ResponseEntity.ok(result);
    } catch (HttpClientErrorException.BadRequest e) {
      return new ResponseEntity<>("One or both cities not found", HttpStatus.NOT_FOUND);
    }
  }



}
