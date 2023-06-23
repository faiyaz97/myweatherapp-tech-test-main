package com.weatherapp.myweatherapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CityInfo {

  @JsonProperty("address")
  String address;

  @JsonProperty("description")
  String description;

  @JsonProperty("currentConditions")
  public CurrentConditions currentConditions;

  @JsonProperty("days")
  List<Days> days;

  public String getAddress() {
        return this.address;
  }

  public CurrentConditions getCurrentConditions() {
    return this.currentConditions;
  }

  public static class CurrentConditions {
    @JsonProperty("temp")
    public String currentTemperature;

    @JsonProperty("sunrise")
    public String sunrise;

    @JsonProperty("sunset")
    public String sunset;

    @JsonProperty("feelslike")
    public String feelslike;

    @JsonProperty("humidity")
    public String humidity;

    @JsonProperty("conditions")
    public String conditions;

    public String getSunrise() {
      return this.sunrise;
    }

    public String getSunset() {
      return this.sunset;
    }
    
    public String getConditions() {
      return this.conditions;
    }
  }

  public static class Days {
    @JsonProperty("datetime")
    public String date;

    @JsonProperty("temp")
    public String currentTemperature;

    @JsonProperty("tempmax")
    public String maxTemperature;

    @JsonProperty("tempmin")
    public String minTemperature;

    @JsonProperty("conditions")
    public String conditions;

    @JsonProperty("description")
    public String description;
  }

}