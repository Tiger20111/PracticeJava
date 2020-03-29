package application1.tests.bd;

import application1.services.bd.DollarRate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Repository
public interface DollarRepository extends CrudRepository<DollarRate, Long> {
  DollarRate findByData(Date data);
  Optional<DollarRate> findById(long primaryKey);
  DollarRate save(DollarRate entity);
  void delete(DollarRate employees);
  ArrayList<DollarRate> findAll();
  long count();
}

