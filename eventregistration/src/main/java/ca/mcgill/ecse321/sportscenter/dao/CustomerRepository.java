package ca.mcgill.ecse321.sportscenter.dao;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportscenter.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    @Query("SELECT c FROM Customer c WHERE c.account.email = :email")
    Customer findCustomerByAccountEmail(String email);
    List<Customer> findAll();
}
