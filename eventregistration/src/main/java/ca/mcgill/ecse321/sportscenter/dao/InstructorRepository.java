package ca.mcgill.ecse321.sportscenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Instructor;

public interface InstructorRepository extends CrudRepository<Instructor, Integer> {
    public Instructor findByAccount(Account account);
}
