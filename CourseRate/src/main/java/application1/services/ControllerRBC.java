package application1.services;

import application1.tests.bd.DollarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class ControllerRBC {

  @Autowired
  private DollarRepository dollarRepository;

  public ControllerRBC() {
    this.service = new ServiceRBC();
  }

  public ControllerRBC(ServiceRBC service) {
    this.service = service;
  }

  @RequestMapping(value = "/course/max", method = GET)
  public Double getMaxCourse() {
    String body = service.getData();
    if (body == null) {
      throw new RuntimeException("404");
    }
    return service.parseRequest(body);
  }

  @RequestMapping(value = "/course/upload", method = GET)
  public String uploadDataBaseDollars() throws Exception {
    String body = service.getData();
    if (body == null) {
      throw new RuntimeException("404");
    }
    return service.saveMonthDollars(body, dollarRepository);
  }

  @RequestMapping(value = "/course/current", method = GET)
  public String printHistoric() throws ParseException {
    String body = service.getData();
    if (body == null) {
      throw new RuntimeException("404");
    }
    return body;
  }
  @RequestMapping(value = "/course/bd", method = GET)
  public String printCoursesBD() throws Exception {
    return service.getCoursesBD(dollarRepository);
  }

  @RequestMapping(value = "/course/{data}", method = GET)
  public String printCourseDay(@PathVariable("data") String data) throws Exception {
    return service.getCourseDay(dollarRepository, data);
  }

  private final ServiceRBC service;
}



