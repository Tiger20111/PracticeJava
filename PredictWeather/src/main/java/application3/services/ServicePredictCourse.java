package application3.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import static application3.services.libs.Utils.FormatDate;
import static application3.services.libs.Utils.numDaysLeft;

@Component
public class ServicePredictCourse {

  ServicePredictCourse() {
  }

  void setServiceUrls(String urlWeather, String urlCourse) {
    this.urlWeather = urlWeather;
    this.urlCourse = urlCourse;
  }


  Double makePrediction(String data) throws Exception {
  double course = 0.0;
    course = getCourseBd(data);
    if (course != -1.0) {
      return course;
    }
    ArrayList<DependencyDollarWeather> dependencyDollarWeathers = getDollarsBD();

    for (DependencyDollarWeather dependencyDollarWeather : dependencyDollarWeathers) {
      dependencyDollarWeather.setPercentage(getTemperatureDate(dependencyDollarWeather.getDate()));
    }

    return analyseByDate(data, dependencyDollarWeathers);

  }

  private ArrayList<DependencyDollarWeather> getDollarsBD() throws IOException {
    String url = urlCourse + "course/bd";
    String dataBD = getUrl(url);
    ArrayList<DependencyDollarWeather> dependencyDollarWeathers = new ArrayList<>();
    for (String dollarRate : dataBD.split("#")) {
      int numEndDate = dollarRate.indexOf("=");
      String date = dollarRate.substring(0, numEndDate);
      Double rate = Double.parseDouble(dollarRate.substring(numEndDate + 1));
      DependencyDollarWeather dependencyDollarWeather = new DependencyDollarWeather(FormatDate(date), rate, 0.0);
      dependencyDollarWeathers.add(dependencyDollarWeather);
    }
    return dependencyDollarWeathers;
  }

  private Double getCourseBd(String data) throws IOException {
    String url = urlCourse + "course/";
    url = url + data;
    String textResponse = getUrl(url);
    double course = 0.0;
    if (textResponse.indexOf('.') == -1) {
      course = -1.0;
    } else {
    course = Double.parseDouble(textResponse);
    }
    return course;
  }

  public String getUrl(String url) throws IOException {
    StringBuffer response = null;
    try {

    URL obj = new URL(url);
    HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

    connection.setRequestMethod("GET");

    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    String inputLine;
    response = new StringBuffer();

    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();

    } catch (Exception e) {
      throw new RuntimeException("wrong address: " + url);
    }
    return response.toString();
  }

  private Double getTemperatureDate(String date) throws IOException {
    String url = urlWeather + "weather/";
    url = url + date;

    String temperature = getUrl(url);

    return Double.parseDouble(temperature);
  }

  private Double analyseByDate(String date, ArrayList<DependencyDollarWeather> dependencyDollarWeathers) throws Exception {
    if (dependencyDollarWeathers.size() == 0) {
      return -1.0;
    }
    int koef = 0;
    Double averageDifference = 0.0;
    for (int i = 0; i < dependencyDollarWeathers.size() - 1; i++) {
      averageDifference += dependencyDollarWeathers.get(i + 1).getPercentage() - dependencyDollarWeathers.get(i).getPercentage();

      koef = 1;
      Random random = SecureRandom.getInstanceStrong();

      if (random.nextInt(1) == 0) {
        koef *= -1;
      }
      averageDifference = averageDifference / dependencyDollarWeathers.size();
    }
    return dependencyDollarWeathers.get(dependencyDollarWeathers.size() - 1).getPercentage()
            + koef * averageDifference *numDaysLeft(date, dependencyDollarWeathers.get(dependencyDollarWeathers.size() - 1).getDate());

  }

  private String urlWeather;
  private String urlCourse;
}
