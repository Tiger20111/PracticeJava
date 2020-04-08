package application2.services.bd;

import javax.persistence.*;
import java.util.Date;

import static application2.services.libs.Utils.FormatData;

@Entity
@Table(name = "weather")
public class WeatherRate {


  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private long id;
  private Date data;
  private Double percentage;


  protected WeatherRate() {}

  public WeatherRate(String data, Double percentage) throws Exception {
    this.data = FormatData(data); // принимает в формате "yyyy/MM/dd"
    this.percentage = percentage;
  }


  public Double getPercentage() {
    return percentage;
  }


  @Override
  public String toString() {
    return String.format(
            "WeatherRate[id=%d, data='%s', percentage='%s']",
            id, data, percentage);
  }
}