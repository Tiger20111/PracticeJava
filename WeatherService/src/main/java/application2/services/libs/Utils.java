package application2.services.libs;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Utils {

  private static Integer tsToSec8601(String timestamp) {
    if (timestamp == null) return null;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
      Date dt = sdf.parse(timestamp);
      long epoch = dt.getTime();
      return (int) (epoch / 1000);
    } catch (ParseException e) {
      return null;
    }
  }

  public static void checkCorrectData(String month, String date) throws Exception {
    if (month.equals("00")) {
      throw new Exception(date);
    }
  }


  private static String formatDateToNight(String date) throws Exception {
    String numDays;
    String numMonths;
    String year;
    try {
      numDays = date.substring(0, 2);
      numMonths = date.substring(3, 5);
      year = date.substring(6, 10);
    } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
      throw new NumberFormatException("Illegal format data: " + date);
    }
    checkCorrectData(numMonths, date);
    return year + "-" + numMonths + "-" + numDays + "T23:59:59.000-0000";
  }

  public static Date FormatData(String date) throws Exception {
    //подается как dd.MM.yyyy
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

  public static Double getDoubleFromString(String line, int startIndex) {
    StringBuilder number = new StringBuilder();
    boolean inNumber = false;
    for(int i = startIndex; i < line.length(); i++) {
      char symbol = line.charAt(i);

      if (Character.isDigit(symbol) && (!inNumber)) {
        inNumber = true;

      } else {
        if (inNumber && !Character.isDigit(symbol)) {
          if(symbol == '.') {
            number.append(symbol);
            continue;
          }
          return Double.parseDouble(number.toString());
        }
      }

      if (inNumber) {
        number.append(symbol);
      }

    }

    return line.length() + 0.0;
  }

  public static String convertToUnix(String data) throws Exception {
    String dataFormatted = formatDateToNight(data);
    int unixTime = tsToSec8601(dataFormatted);
    return Integer.toString(unixTime);
  }

}
