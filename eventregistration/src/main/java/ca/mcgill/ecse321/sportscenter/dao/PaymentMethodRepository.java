package ca.mcgill.ecse321.sportscenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportscenter.model.PaymentMethod;

public interface PaymentMethodRepository extends CrudRepository<PaymentMethod, Integer> {

}
