package application1.services.libs;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Utils {


  public static void checkCorrectData(String month, String date) throws Exception {
    if (month.equals("00")) {
      throw new Exception(date);
    }
  }

  public static Date FormatData(String date) throws Exception {
    //подается как dd/MM/yyyy
    int numDays = 0;
    int numMonths = 0;
    int year = 0;
    try {
      numDays = Integer.parseInt(date.substring(0, 2));
      numMonths = Integer.parseInt(date.substring(3, 5));
      year = Integer.parseInt(date.substring(6, 10));
    } catch (Exception e) {
      throw new ParseException("Can not parse:" + date, 4);
    }
    String formalDate = Integer.toString(year) + "/" + Integer.toString(numMonths) + "/" + Integer.toString(numDays);
    checkCorrectData(date.substring(3, 5), date);
    return new SimpleDateFormat("yyyy/MM/dd").parse(formalDate);
  }

  public static String formatDateFromDollarPrint(String date) throws Exception {
    //принимает в формате yyyy-mm-dd
    String year;
    String month;
    String day;
    year = date.substring(0, 4);
    month = date.substring(6, 7);
    day = date.substring(9, 10);
    if (day.length() == 1) {
      day = '0' + day;
    }
    if (month.length() == 1) {
      if (month.equals("0")) {
        throw new Exception(date);
      }
      month = '0' + month;
    }

    return day + "/" + month + "/" + year;
  }

}
