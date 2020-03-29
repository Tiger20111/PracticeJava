package application2.services;

import application2.services.bd.WeatherRate;
import application2.services.bd.WeatherRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.TreeMap;

import static application2.services.libs.Utils.*;


@Component
public class ServiceWeather {


  public ServiceWeather() {

  }

  private String getTemperatures(String data) throws Exception {
    String unixTime = convertToUnix(data);
    String url = apiForecast + hash + location + unixTime + flag;
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
    return response.getBody();
  }

  public Double getTemperatureDate(String data, WeatherRepository weatherRepository) throws Exception {

    if (weatherRepository == null) {
      return  -1.0;
    }
    WeatherRate weatherRate = weatherRepository.findByData(FormatData(data));


    if (weatherRate != null) {
      return weatherRate.getPercentage();
    }


    String request = getTemperatures(data);

    TreeMap<Double, Double> temperatures =  parseTemperatures(request);
    double sumTemp = 0;

    for (Map.Entry<Double, Double> tempr : temperatures.entrySet()) {
      sumTemp += tempr.getValue();
    }

    double averageRate = sumTemp / temperatures.size();

    WeatherRate weatherRateNew = new WeatherRate(data, averageRate);
    weatherRepository.save(weatherRateNew);

    return averageRate;

  }


  private TreeMap<Double, Double> parseTemperatures(String request) {
    String[] lines = request.split("apparentTemperature");
    TreeMap<Double, Double> temperatures= new TreeMap<>();
    int timeIndex;
    for (String line : lines) {
      int temperatureIndex = line.lastIndexOf("\"temperature\"");
      if (temperatureIndex == -1) {
        continue;
      } else {
        timeIndex = line.lastIndexOf("\"time\"");
        if (timeIndex == -1) {
          continue;
        }
      }


      temperatures.put(getDoubleFromString(line, timeIndex), getDoubleFromString(line, temperatureIndex));
    }
    return temperatures;
  }

  private static final String apiForecast = "https://api.darksky.net/forecast/";
  private static final String location = "/40.7127,-74.0059,";
  private static final String flag = "?exclude=currently,flag";
  private static final String hash = "6880e28eeb567a5ffda54af015126cf6";

  //Там юзается unix time. У нас в день 24*60*60 = 86400 секунд. На 01 января 01 декабря 2019 года приходится 1546300800.
}
