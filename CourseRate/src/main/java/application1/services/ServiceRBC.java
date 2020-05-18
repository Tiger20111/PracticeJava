package application1.services;

import application1.services.bd.DollarRate;
import application1.services.bd.DollarRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;

import static application1.services.libs.Utils.FormatData;
import static application1.services.libs.Utils.formatDateFromDollarPrint;


public class ServiceRBC {

  public ServiceRBC() {
    this.restTemplate = new RestTemplate();
  }

  public String getData() {

    String urlExport = "http://export.rbc.ru/free/selt.0/free.fcgi?period=DAILY&tickers=USD000000TOD&d1=01&m1=07&y1=2019&d2=01&m2=10&y2=2019&separator=TAB&data_format=BROWSER";
    ResponseEntity<String> response = restTemplate.getForEntity(urlExport, String.class);

    return response.getBody();

  }

  String saveExamplesBd(DollarRepository dollarRepository) throws Exception {
    if (dollarRepository.count() != 0) {
      return "Already updated";
    }
    ArrayList<DollarRate> dollarRates = new ArrayList<>();
    dollarRates.add(new DollarRate(formatDateFromDollarPrint("2020-05-08"), 33.0));
    dollarRates.add(new DollarRate(formatDateFromDollarPrint("2020-05-09"), 32.0));
    dollarRates.add(new DollarRate(formatDateFromDollarPrint("2020-05-10"), 34.0));
    dollarRates.add(new DollarRate(formatDateFromDollarPrint("2020-05-11"), 35.0));
    dollarRates.add(new DollarRate(formatDateFromDollarPrint("2020-05-12"), 36.0));
    dollarRates.add(new DollarRate(formatDateFromDollarPrint("2020-05-13"), 33.0));
    dollarRates.add(new DollarRate(formatDateFromDollarPrint("2020-05-14"), 32.0));
    dollarRates.add(new DollarRate(formatDateFromDollarPrint("2020-05-15"), 31.0));
    dollarRates.add(new DollarRate(formatDateFromDollarPrint("2020-05-16"), 30.0));
    dollarRates.add(new DollarRate(formatDateFromDollarPrint("2020-05-17"), 32.0));
    dollarRates.add(new DollarRate(formatDateFromDollarPrint("2020-05-18"), 34.0));
    dollarRates.add(new DollarRate(formatDateFromDollarPrint("2020-05-19"), 35.0));
    dollarRates.add(new DollarRate(formatDateFromDollarPrint("2020-05-20"), 36.0));
    dollarRates.add(new DollarRate(formatDateFromDollarPrint("2020-05-21"), 37.0));
    dollarRates.add(new DollarRate(formatDateFromDollarPrint("2020-05-22"), 38.0));
    dollarRates.add(new DollarRate(formatDateFromDollarPrint("2020-05-23"), 30.0));
    dollarRates.add(new DollarRate(formatDateFromDollarPrint("2020-05-24"), 40.0));
    dollarRates.add(new DollarRate(formatDateFromDollarPrint("2020-05-25"), 43.0));
    dollarRates.add(new DollarRate(formatDateFromDollarPrint("2020-05-27"), 43.0));
    dollarRates.add(new DollarRate(formatDateFromDollarPrint("2020-05-26"), 43.0));
    dollarRates.add(new DollarRate(formatDateFromDollarPrint("2020-05-28"), 42.0));
    dollarRates.add(new DollarRate(formatDateFromDollarPrint("2020-05-29"), 41.0));
    dollarRates.add(new DollarRate(formatDateFromDollarPrint("2020-05-30"), 40.0));
    dollarRepository.saveAll(dollarRates);
    return "Done";
  }

  public String getCourseDay(DollarRepository dollarRepository, String data) throws Exception {
    Date date;
    try {
      date = FormatData(data);
    } catch (Exception e) {
      throw new RuntimeException("parse low");
    }
    int error_count = 0;
    try {
      DollarRate dollarRate = dollarRepository.findByDate(date);
      if (dollarRate == null) {
        //saveMonthDollars(getData(), dollarRepository);
        saveExamplesBd(dollarRepository);
        dollarRate = dollarRepository.findByDate(date);
        if (dollarRate == null) {
          return "no data in base";
        }
      }
      return dollarRate.getPercentage().toString();
    } catch (Exception e) {
      String allDate = "";
      ArrayList<DollarRate> dollarRates = dollarRepository.findAll();
      for (DollarRate dollarRate : dollarRates) {
        allDate += dollarRate.getDate().toString() + "\n\n";
        if (dollarRate.getDate().equals(date)) {
          error_count++;
        }
      }
      DollarRate dollarRate1 = new DollarRate(data, 2.0);
      throw new RuntimeException("BD is not working: " + date.toString() + "-" + dollarRate1.getDate() + "-" + "( " + error_count + " )" + " " + e.getMessage() + "\n\n\n" + allDate);
    }
  }

  public String getCoursesBD(DollarRepository dollarRepository) throws Exception {
    StringBuilder coursesBD = new StringBuilder();
    if (dollarRepository.count() == 0) {
//      saveMonthDollars(getData(), dollarRepository);
      saveExamplesBd(dollarRepository);
    }
    ArrayList<DollarRate> dollarRates = dollarRepository.findAll();
    for (DollarRate dollarRate : dollarRates) {
      coursesBD.append(dollarRate.getDate()).append("=").append(dollarRate.getPercentage().toString()).append("#");
    }
    return coursesBD.substring(0, coursesBD.length() - 1).toString();
  }

  public String saveMonthDollars(String body, DollarRepository dollarRepository) throws Exception {
    if (dollarRepository.count() != 0) {
      return "The bd has been uploaded earlier";
    }
    String[] lines = body.split("\n");
    ArrayList<DollarRate> dollarRates = new ArrayList<>();
    double sumDollars = 0;
    int numDays = 0;
    String date = "";
    for (String line : lines) {
      String hm = line.substring(12, 13);
      String[] words = line.split(hm);

      for (String word : words) {
        if (isNewDate(word)) {
          if (numDays != 0) {
            boolean repeat = false;
            DollarRate dollarRate1 = new DollarRate(formatDateFromDollarPrint(date), (sumDollars / numDays));
            for (DollarRate dollarRate : dollarRates) {
              if (dollarRate.getDate().equals(dollarRate1.getDate())) {
                dollarRate.setPercentage((dollarRate.getPercentage() +  dollarRate1.getPercentage()) / 2);
                repeat = true;
                break;
              }
            }
            if (!repeat) {
              dollarRates.add(dollarRate1);
            }
            sumDollars = 0;
            numDays = 0;
          }

          date = word;
        } else {
          if (isRateDollar(word)) {
            numDays += 1;
            sumDollars += Double.parseDouble(word);
          }
        }
      }
    }

    if (dollarRates.size() != 0) {
      dollarRepository.saveAll(dollarRates);
    }
    String out = "";
    for (DollarRate dollarRate : dollarRates) {
      out += dollarRate.getDate().toString() + "\n";
    }
    return out;

  }

  private Boolean isRateDollar(String word) {
    int numPoints = 0;
    for (int i = 0; i < word.length(); i++) {
      if (word.charAt(i) == '.') {
        numPoints++;
      }
    }
    return numPoints == 1;
  }

  private Boolean isNewDate(String word) {
    int numDash = 0;
    for (int i = 0; i < word.length(); i++) {
      if (word.charAt(i) == '-') {
        numDash++;
      }
    }
    return numDash == 2;
  }

  Double parseRequest(String body) {
    String[] lines = body.split("\n");

    double maxCourse = -1;
    for (String line : lines) {
      String[] parts = line.split("\\s");
      double currentCourse = Double.parseDouble(parts[parts.length - 1]);

      if (maxCourse < currentCourse || maxCourse == -1) {
        maxCourse = currentCourse;
      }
    }

    return maxCourse;
  }

  private final RestTemplate restTemplate;
}
