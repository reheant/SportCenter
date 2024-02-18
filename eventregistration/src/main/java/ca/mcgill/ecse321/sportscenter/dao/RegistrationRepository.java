package ca.mcgill.ecse321.sportscenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportscenter.model.Registration;

public interface RegistrationRepository extends CrudRepository<Registration, Integer> {

}
