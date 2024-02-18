package ca.mcgill.ecse321.sportscenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportscenter.model.PayPal;

public interface PayPalRepository extends CrudRepository<PayPal, Integer> {

}
