package com.weatherapp.myweatherapp.service;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.repository.VisualcrossingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class WeatherServiceTest {

  private WeatherService weatherService;

  @Mock
  private VisualcrossingRepository weatherRepo;

  @Mock
  private CityInfo cityInfo1, cityInfo2;

  @Mock
  private CityInfo.CurrentConditions currentConditions1, currentConditions2;

  @BeforeEach
  void setup() {
      MockitoAnnotations.openMocks(this);

      // Given for all tests
      when(cityInfo1.getAddress()).thenReturn("City1");
      when(cityInfo1.getCurrentConditions()).thenReturn(currentConditions1);
      
      when(cityInfo2.getAddress()).thenReturn("City2");
      when(cityInfo2.getCurrentConditions()).thenReturn(currentConditions2);
    
      when(weatherRepo.getByCity("City1")).thenReturn(cityInfo1);
      when(weatherRepo.getByCity("City2")).thenReturn(cityInfo2);

      // Initialize WeatherService and inject mock repository using ReflectionTestUtils
      weatherService = new WeatherService();
      ReflectionTestUtils.setField(weatherService, "weatherRepo", weatherRepo);
  }

  @Test
  void compareDaylightHours_City1HasLongerDaylight() {
    // Given
    when(currentConditions1.getSunrise()).thenReturn("06:00:00");
    when(currentConditions1.getSunset()).thenReturn("18:00:00");
    when(currentConditions2.getSunrise()).thenReturn("07:00:00");
    when(currentConditions2.getSunset()).thenReturn("17:00:00");

    // When
    String result = weatherService.compareDaylightHours("City1", "City2");
    assertEquals("City1 has longer daylight hours.", result);
  }

  void compareDaylightHours_City2HasLongerDaylight() {
    // Given
    when(currentConditions1.getSunrise()).thenReturn("07:00:00");
    when(currentConditions1.getSunset()).thenReturn("17:00:00");
    when(currentConditions2.getSunrise()).thenReturn("06:00:00");
    when(currentConditions2.getSunset()).thenReturn("18:00:00");

    // When
    String result = weatherService.compareDaylightHours("City1", "City2");
    assertEquals("City2 has longer daylight hours.", result);
  }

  void compareDaylightHours_BothCitiesHaveEqualDaylight() {
    // Given
    when(currentConditions1.getSunrise()).thenReturn("07:00:00");
    when(currentConditions1.getSunset()).thenReturn("17:00:00");
    when(currentConditions2.getSunrise()).thenReturn("07:00:00");
    when(currentConditions2.getSunset()).thenReturn("17:00:00");

    // When
    String result = weatherService.compareDaylightHours("City1", "City2");
    assertEquals("Both cities have equal daylight hours.", result);
  }

  @Test
  void testCheckRainBothCities() {
    // Given
    when(currentConditions1.getConditions()).thenReturn("Rain");
    when(currentConditions2.getConditions()).thenReturn("Rain");

    // When
    String result = weatherService.checkRain("City1", "City2");

    // Then
    assertEquals("It is currently raining in both City1 and City2.", result);
  }

  @Test
  void testCheckRainNeitherCity() {
    // Given
    when(currentConditions1.getConditions()).thenReturn("Sunny");
    when(currentConditions2.getConditions()).thenReturn("Sunny");

    // When
    String result = weatherService.checkRain("City1", "City2");

    // Then
    assertEquals("It is currently not raining in either City1 or City2.", result);
  }

  @Test
  void testCheckRainFirstCity() {
    // Given
    when(currentConditions1.getConditions()).thenReturn("Rain");
    when(currentConditions2.getConditions()).thenReturn("Sunny");

    // When
    String result = weatherService.checkRain("City1", "City2");

    // Then
    assertEquals("It is currently raining in City1.", result);
  }

  @Test
  void testCheckRainSecondCity() {
    // Given
    when(currentConditions1.getConditions()).thenReturn("Sunny");
    when(currentConditions2.getConditions()).thenReturn("Rain");

    // When
    String result = weatherService.checkRain("City1", "City2");

    // Then
    assertEquals("It is currently raining in City2.", result);
  }

  
}
