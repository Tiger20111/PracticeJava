package application2.services.bd;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface WeatherRepository extends CrudRepository<WeatherRate, Long> {
  WeatherRate findByData(Date data);
  Optional<WeatherRate> findById(long id);
  WeatherRate save(WeatherRate entity);
  void delete(WeatherRate employees);
}
