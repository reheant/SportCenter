package ca.mcgill.ecse321.sportscenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportscenter.model.InstructorAssignment;

public interface InstructorAssignmentRepository
        extends CrudRepository<InstructorAssignment, Integer> {

}
