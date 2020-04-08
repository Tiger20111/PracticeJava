package application1.services.bd;


import javax.persistence.*;
import java.util.Date;

import static application1.services.libs.Utils.FormatData;


@Entity
@Table(name = "dollar")
public class DollarRate {


  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private long id;
  private Date data;
  private Double course;


  protected DollarRate() {}

  public DollarRate(String data, Double course) throws Exception {
    this.data = FormatData(data); // принимает в формате "yyyy/MM/dd"
    this.course = course;
  }


  public Double getPercentage() {
    return course;
  }
  public Date getData() {
    return data;
  }

  public void setPercentage(double course) {
    this.course = course;
  }

  @Override
  public String toString() {
    return String.format(
            "DollarRate[id=%d, data='%s', course='%s']",
            id, data, course);
  }
}
