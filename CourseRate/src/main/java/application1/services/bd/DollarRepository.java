package application1.services.bd;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Repository
public interface DollarRepository extends CrudRepository<DollarRate, Long> {
  DollarRate findByDate(Date date);
  Optional<DollarRate> findById(long primaryKey);
  DollarRate save(DollarRate entity);
  void delete(DollarRate employees);
  ArrayList<DollarRate> findAll();
  long count();
}

