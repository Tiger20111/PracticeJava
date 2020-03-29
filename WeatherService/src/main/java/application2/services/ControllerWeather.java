package application2.services;

import application2.services.bd.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


@RestController
public class ControllerWeather {
  @Autowired
  private WeatherRepository repWeather;



  public ControllerWeather() {
    this.service = new ServiceWeather();
  }

  @RequestMapping(value = "/weather/{data}", method = GET)
  public Double getWeather(@PathVariable("data") String data) throws Exception {
    return service.getTemperatureDate(data, repWeather);
  }

  private final ServiceWeather service;
}
