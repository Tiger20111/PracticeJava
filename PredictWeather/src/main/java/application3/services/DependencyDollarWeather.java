package application3.services;

import org.springframework.stereotype.Component;

@Component
public class DependencyDollarWeather {

  DependencyDollarWeather(String date, Double rate, Double percentage) {
    this.date = date;
    this.rate = rate;
    this.percentage = percentage;
  }

  DependencyDollarWeather() {
    date = "";
    rate = 0.0;
    percentage = 0.0;
  }

  private String date;
  private Double rate;
  private Double percentage;

  public String  setData(String date) {
    this.date = date;
    return date;
  }

  public Double setRate(Double rate) {
    this.rate = rate;
    return rate;
  }

  public Double setPercentage(Double percentage) {
    this.percentage = percentage;
    return percentage;
  }

  public Double getPercentage() {
    return percentage;
  }

  public Double getRate() {
    return rate;
  }

  public String getDate() {
    return date;
  }
}
