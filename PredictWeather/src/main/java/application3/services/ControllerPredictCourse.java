package application3.services;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

  private final ServicePredictCourse service;

}
