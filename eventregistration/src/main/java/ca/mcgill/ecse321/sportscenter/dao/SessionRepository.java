package ca.mcgill.ecse321.sportscenter.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportscenter.model.Session;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface SessionRepository extends CrudRepository<Session, Integer> {

    Session findSessionById(Integer id);
    List<Session> findSessionsByIdIn(Collection<Integer> ids);
    List<Session> findSessionsByCourseNameContainingIgnoreCase(String name);
    List<Session> findSessionsByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

  
}

