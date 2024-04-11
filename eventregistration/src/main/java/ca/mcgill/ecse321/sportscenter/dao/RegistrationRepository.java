package ca.mcgill.ecse321.sportscenter.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportscenter.model.Registration;

public interface RegistrationRepository extends CrudRepository<Registration, Integer> {
    @Query("SELECT r FROM Registration r WHERE r.customer.account.email = :email AND r.session.id = :sessionId")
    Registration findRegistrationByCustomerAccountEmailAndSessionId(String email, Integer sessionId);    
    List<Registration> findAll();
    List<Registration> findAllByCustomerId(Integer customerId);
}
