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

    Session findSessionById(Integer id);
    List<Session> findSessionsByIdIn(Collection<Integer> ids);
    List<Session> findSessionsByCourseNameContainingIgnoreCase(String name);
    List<Session> findSessionsByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
}
