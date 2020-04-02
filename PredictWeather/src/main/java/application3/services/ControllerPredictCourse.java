package application3.services;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class ControllerPredictCourse {
  public ControllerPredictCourse() {
    this.service = new ServicePredictCourse();
  }
  public ControllerPredictCourse(ServicePredictCourse service) {
    this.service = service;
  }

  @RequestMapping(value = "/predict/{data}", method = GET)
  public Double predictDollar(@PathVariable("data") String data) throws Exception {
    return service.makePrediction(data);
  }

  @RequestMapping(value = "/predict/work")
  public Boolean isAvailable() {
    return true;
  }

  @RequestMapping(value = "/predict/test/connection/weather")
  public Boolean isConnectWeather() throws IOException {
    String url = "http://course:8081/course/max";
    service.getUrl(url);
    return true;
  }

  private final ServicePredictCourse service;

}
