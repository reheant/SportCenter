package ca.mcgill.ecse321.sportscenter.service;

import ca.mcgill.ecse321.sportscenter.dao.CourseRepository;
import ca.mcgill.ecse321.sportscenter.dao.InstructorAssignmentRepository;
import ca.mcgill.ecse321.sportscenter.dao.LocationRepository;
import ca.mcgill.ecse321.sportscenter.dao.SessionRepository;
import ca.mcgill.ecse321.sportscenter.model.InstructorAssignment;
import ca.mcgill.ecse321.sportscenter.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SessionService {
    @Autowired
    InstructorAssignmentRepository instructorAssignmentRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    LocationRepository locationRepository;

    /**
     * Retrieves all sessions from the repository.
     *
     * @return A list of all sessions.
     * @throws Exception if an error occurs during retrieval.
     */
    @Transactional
    public List<Session> getAllSessions() throws Exception {
        List<Session> sessions = new ArrayList<>();
        sessionRepository.findAll().forEach(session -> sessions.add(session));
        return sessions;
    }

    /**
     * Filters sessions based on provided criteria. Sessions can be filtered by ID, name, date, duration, or instructor name.
     * Filters are applied in order of parameter listing if multiple filters are provided.
     *
     * @param ids A collection of session IDs to filter by.
     * @param name The name to filter sessions by, typically the course name.
     * @param date The date to filter sessions by. Only sessions on this date are returned.
     * @param duration The exact duration in minutes to filter sessions by.
     * @param instructor The full name of the instructor to filter sessions by.
     * @return A list of sessions that match the provided filter criteria, without duplicates.
     * @throws Exception if no matches are found or if an error occurs during filtering.
     */
    @Transactional
    public List<Session> viewFilteredSessions(
            Collection<Integer> ids, String name, LocalDate date, Float duration, String instructor) throws Exception {
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
            List<Session> byDuration = findSessionsByDuration(duration);
            filteredSessions.addAll(byDuration);
        }
        if (instructor != null) {
            List<Session> byInstructor = findSessionsByInstructorName(instructor);
            filteredSessions.addAll(byInstructor);
        }
        if (filteredSessions == null) {
            throw new Exception("No matches found.");
        }
        return filteredSessions.stream().distinct().collect(Collectors.toList());
    }

    /**
     * Finds sessions that have an exact duration matching the specified number of minutes.
     *
     * @param durationInMinutes The duration in minutes to filter sessions by.
     * @return A list of sessions that exactly match the specified duration.
     */
    public List<Session> findSessionsByDuration(Float durationInMinutes) {
        List<Session> allSessions = (List<Session>) sessionRepository.findAll();
        return allSessions.stream()
                .filter(session -> {
                    Duration sessionDuration = Duration.between(session.getStartTime(), session.getEndTime());
                    return sessionDuration.toMinutes() == durationInMinutes;
                })
                .collect(Collectors.toList());
    }

    public List<InstructorAssignment> findAssignmentsForSession(Session session) {
        return instructorAssignmentRepository.findInstructorAssignmentBySessionId(session.getId());
    }

    public List<Session> findSessionsByInstructorName(String instructor) {
        List<InstructorAssignment> assignments = instructorAssignmentRepository.findInstructorAssignmentByInstructorNameContainingIgnoreCase(instructor);
        if (assignments.isEmpty()) {
            return Collections.emptyList();
        }
        Set<Integer> assignmentIds = assignments.stream()
                .map(InstructorAssignment::getId)
                .collect(Collectors.toSet());
        List<Session> allSessions = (List<Session>) sessionRepository.findAll();
        List<Session> matchedSessions = new ArrayList<>();
        for (Session session : allSessions) {
            List<InstructorAssignment> assignmentsForSession = findAssignmentsForSession(session);
            for (InstructorAssignment assignment : assignmentsForSession) {
                if (assignmentIds.contains(assignment.getId())) {
                    matchedSessions.add(session);
                    break;
                }
            }
        }
        return matchedSessions;
    }

    /**
     * Creates and persists a new session with the specified start time, end time, course name, and location name.
     *
     * @param startTime The start time of the new session.
     * @param endTime The end time of the new session.
     * @param courseName The name of the course associated with the new session.
     * @param locationName The name of the location where the session will take place.
     * @return The newly created session.
     * @throws Exception if time bounds are not provided, if start time is after or equal to end time,
     * or if there is an error during session creation.
     */
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

    /**
     * Deletes a session by its ID.
     *
     * @param sessionId The ID of the session to be deleted.
     */
    public void deleteSession(Integer sessionId) {
        sessionRepository.deleteById(sessionId);
    }
}
