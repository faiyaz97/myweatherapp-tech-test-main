package com.weatherapp.myweatherapp.service;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.repository.VisualcrossingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalTime;



@Service
public class WeatherService {

  @Autowired
  VisualcrossingRepository weatherRepo;

  public CityInfo forecastByCity(String city) {

    return weatherRepo.getByCity(city);
  }

  public String compareDaylightHours(String city1, String city2) {
    CityInfo cityInfo1 = weatherRepo.getByCity(city1);
    CityInfo cityInfo2 = weatherRepo.getByCity(city2);

    LocalTime city1SunriseTime = LocalTime.parse(cityInfo1.getCurrentConditions().getSunrise());
    LocalTime city1SunsetTime = LocalTime.parse(cityInfo1.getCurrentConditions().getSunset());

    LocalTime city2SunriseTime = LocalTime.parse(cityInfo2.getCurrentConditions().getSunrise());
    LocalTime city2SunsetTime = LocalTime.parse(cityInfo2.getCurrentConditions().getSunset());

    Duration city1DaylightDuration = Duration.between(city1SunriseTime, city1SunsetTime);
    Duration city2DaylightDuration = Duration.between(city2SunriseTime, city2SunsetTime);

    int comparisonResult = city1DaylightDuration.compareTo(city2DaylightDuration);

    if (comparisonResult > 0) {
        return(cityInfo1.getAddress() + " has longer daylight hours.");
    } else if (comparisonResult < 0) {
        return(cityInfo2.getAddress() + " has longer daylight hours.");
    } else {
        return("Both " + cityInfo1.getAddress() + " and " + cityInfo2.getAddress() + " have the same daylight hours.");
    }
  }



  public String checkRain(String city1, String city2) {
    CityInfo cityInfo1 = weatherRepo.getByCity(city1);
    CityInfo cityInfo2 = weatherRepo.getByCity(city2);

    boolean isRainingInCity1 = cityInfo1.getCurrentConditions().getConditions().toLowerCase().contains("rain");
    boolean isRainingInCity2 = cityInfo2.getCurrentConditions().getConditions().toLowerCase().contains("rain");

    if (isRainingInCity1 && isRainingInCity2) {
      return ("It is currently raining in both " + cityInfo1.getAddress() + " and " + cityInfo2.getAddress() + ".");
    } else if (isRainingInCity1) {
      return ("It is currently raining in " + cityInfo1.getAddress() + ".");
    } else if (isRainingInCity2) {
      return ("It is currently raining in " + cityInfo2.getAddress() + ".");
    } else {
      return ("It is currently not raining in either " + cityInfo1.getAddress() + " or " + cityInfo2.getAddress() + ".");
    }
  }


}


