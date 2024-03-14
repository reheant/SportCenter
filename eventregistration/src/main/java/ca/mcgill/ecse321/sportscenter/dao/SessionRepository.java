package ca.mcgill.ecse321.sportscenter.dao;

import ca.mcgill.ecse321.sportscenter.model.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportscenter.model.Session;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface SessionRepository extends CrudRepository<Session, Integer> {
    List<Session> findSessionsByIdIn(Collection<Integer> ids);
    List<Session> findSessionsByCourseNameContainingIgnoreCase(String name);
    List<Session> findSessionsByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
    @Query("SELECT s FROM Session s WHERE FUNCTION('TIMESTAMPDIFF', MINUTE, s.startTime, s.endTime) >= :duration")
    List<Session> findSessionsByDuration(Float duration);
    @Query("SELECT s FROM Session s " +
            "LEFT JOIN s.instructorAssignment ia " +
            "LEFT JOIN ia.instructor i " +
            "LEFT JOIN i.account a " +
            "WHERE LOWER(a.firstName) LIKE LOWER(CONCAT('%',:name,'%')) " +
            "OR LOWER(a.lastName) LIKE LOWER(CONCAT('%',:name,'%'))")
    List<Session> findByInstructorNameContainingIgnoreCase(@Param("name") String name);
}
