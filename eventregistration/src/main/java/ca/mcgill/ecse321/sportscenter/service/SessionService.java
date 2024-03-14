package ca.mcgill.ecse321.sportscenter.service;

import ca.mcgill.ecse321.sportscenter.dao.CourseRepository;
import ca.mcgill.ecse321.sportscenter.dao.LocationRepository;
import ca.mcgill.ecse321.sportscenter.dao.SessionRepository;
import ca.mcgill.ecse321.sportscenter.model.Instructor;
import ca.mcgill.ecse321.sportscenter.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SessionService {
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    LocationRepository locationRepository;
    @Transactional
    public List<Session> getAllSessions() throws Exception {
        List<Session> sessions = new ArrayList<>();
        sessionRepository.findAll().forEach(session -> sessions.add(session));
        return sessions;
    }

    @Transactional
    public List<Session> viewFilteredSessions(
            Collection<Integer> ids, String name, LocalDate date, Float duration, String instructorFirstName, String instructorLastName) throws Exception {
        List<Session> filteredSessions = new ArrayList<>();
        if (ids != null) {
            List<Session> byId = sessionRepository.findSessionsByIdIn(ids);
            filteredSessions.addAll(byId);
            if (filteredSessions == null) {
                throw new Exception("No matches found.");
            }
            return filteredSessions;
        }
        if (name != null) {
            List<Session> byName = sessionRepository.findSessionsByCourseNameContainingIgnoreCase(name);
            filteredSessions.addAll(byName);
        }
        if (date != null) {
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
            List<Session> byDate = sessionRepository.findSessionsByStartTimeBetween(startOfDay, endOfDay);
            filteredSessions.addAll(byDate);
        }
        if (duration != null) {
            List<Session> byDuration = sessionRepository.findSessionsByDuration(duration);
            filteredSessions.addAll(byDuration);
        }
        if (instructorFirstName != null || instructorLastName != null) {
            List<Session> byInstructor = sessionRepository.findByInstructorNameContainingIgnoreCase(instructorFirstName);
            filteredSessions.addAll(byInstructor);
        }
        if (filteredSessions == null) {
            throw new Exception("No matches found.");
        }
        return filteredSessions.stream().distinct().collect(Collectors.toList());
    }

    @Transactional
    public Session createSession(LocalDateTime startTime, LocalDateTime endTime, String courseName, String locationName) throws Exception {
        if (startTime == null || endTime == null) {
            throw new Exception("The session requires time bounds.");
        }
        if (startTime.isAfter(endTime) || startTime.isEqual(endTime)) {
            throw new Exception("The time bounds are invalid.");
        }
        Session session = new Session();
        session.setStartTime(startTime);
        session.setEndTime(endTime);
        session.setCourse(courseRepository.findCourseByName(courseName));
        session.setLocation(locationRepository.findLocationByName(locationName));
        sessionRepository.save(session);
        return session;
    }

    public void deleteSession(Integer sessionId) {
        sessionRepository.deleteById(sessionId);
    }
}
