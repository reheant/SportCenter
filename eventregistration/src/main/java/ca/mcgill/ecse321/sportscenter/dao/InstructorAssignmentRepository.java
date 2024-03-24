package ca.mcgill.ecse321.sportscenter.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportscenter.model.InstructorAssignment;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InstructorAssignmentRepository
        extends CrudRepository<InstructorAssignment, Integer> {
    List<InstructorAssignment> findInstructorAssignmentBySessionId(Integer id);
    @Query("SELECT ia FROM InstructorAssignment ia WHERE LOWER(CONCAT(ia.instructor.account.firstName, ' ', ia.instructor.account.lastName)) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<InstructorAssignment> findInstructorAssignmentByInstructorNameContainingIgnoreCase(@Param("name") String name);
}
