package ca.mcgill.ecse321.sportscenter.dao;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Instructor;
import java.util.Optional;

public interface InstructorRepository extends CrudRepository<Instructor, Integer> {
    public Instructor findByAccount(Account account);
    Instructor findInstructorByAccountEmail(String email);
    List<Instructor> findAll();
    Optional<Instructor> findByAccountId(Integer accountId);
}
