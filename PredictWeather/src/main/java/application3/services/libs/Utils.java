package application3.services.libs;

import java.io.IOException;

public class Utils {

  public static String FormatDate(String date) throws IOException {
    String year = date.substring(0, 4);
    String numMonths = date.substring(5, 7);
    String numDays = date.substring(8, 10);
    return numDays + "." + numMonths + "." + year;
  }

  public static Integer numDaysLeft(String date1, String date2) {
    int numDays1 = Integer.parseInt(date1.substring(0, 2));
    int numMonths1 = Integer.parseInt(date1.substring(3, 5));
    int year1 = Integer.parseInt(date1.substring(6, 10));

    int numDays2 = Integer.parseInt(date2.substring(0, 2));
    int numMonths2 = Integer.parseInt(date2.substring(3, 5));
    int year2 = Integer.parseInt(date2.substring(6, 10));

    int days = (year2 - year1) * 365 + (numMonths2 - numMonths1) * 30 + (numDays2 - numDays1);
    if (days < 0) {
      days = days * (-1);
    }
    return days;
  }


}
