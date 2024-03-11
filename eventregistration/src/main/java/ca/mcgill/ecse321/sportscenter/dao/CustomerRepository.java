package ca.mcgill.ecse321.sportscenter.dao;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportscenter.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    //Customer findCustomerByEmail(String email);
    List<Customer> findAll();
}
