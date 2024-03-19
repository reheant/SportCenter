package ca.mcgill.ecse321.sportscenter.service;

import ca.mcgill.ecse321.sportscenter.dao.CourseRepository;
import ca.mcgill.ecse321.sportscenter.dao.InstructorAssignmentRepository;
import ca.mcgill.ecse321.sportscenter.dao.LocationRepository;
import ca.mcgill.ecse321.sportscenter.dao.SessionRepository;
import ca.mcgill.ecse321.sportscenter.model.InstructorAssignment;
import ca.mcgill.ecse321.sportscenter.model.Session;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestSessionService {
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private InstructorAssignmentRepository instructorAssignmentRepository;

    @InjectMocks
    private SessionService sessionService;

    @Test
    public void testGetAllSessions() throws Exception {
        Session session1 = new Session();
        Session session2 = new Session();
        when(sessionRepository.findAll()).thenReturn(Arrays.asList(session1, session2));
        List<Session> sessions = sessionService.getAllSessions();
        assertNotNull(sessions);
        assertEquals(2, sessions.size());
        verify(sessionRepository).findAll();
    }

    @Test
    void testViewFilteredSessions() throws Exception {
        SessionService sessionService = new SessionService();
        sessionService.sessionRepository = sessionRepository;
        Session session1 = new Session();
        session1.setStartTime(LocalDateTime.of(2024, 3, 15, 13, 0)); // Example start time
        session1.setEndTime(LocalDateTime.of(2024, 3, 15, 14, 0));   // Example end time
        LocalDate filterDate = LocalDate.of(2024, 3, 15);
        LocalDateTime startOfDay = filterDate.atStartOfDay();
        LocalDateTime endOfDay = filterDate.atTime(LocalTime.MAX);
        when(sessionRepository.findSessionsByStartTimeBetween(startOfDay, endOfDay)).thenReturn(Collections.singletonList(session1));
        List<Session> sessions = sessionService.viewFilteredSessions(null, null, filterDate, null, null);
        assertNotNull(sessions, "The returned session list should not be null.");
        assertEquals(1, sessions.size());
        assertEquals(session1, sessions.get(0));
    }

    @Test
    void testFindSessionsByDuration() {
        SessionService sessionService = new SessionService();
        sessionService.sessionRepository = sessionRepository;
        Session session1 = new Session();
        session1.setStartTime(LocalDateTime.of(2024, 5, 15, 10, 0));
        session1.setEndTime(LocalDateTime.of(2024, 5, 15, 11, 0));
        when(sessionRepository.findAll()).thenReturn(Arrays.asList(session1));
        List<Session> matchedSessions = sessionService.findSessionsByDuration(60f);
        assertEquals(1, matchedSessions.size());
        assertTrue(matchedSessions.contains(session1));
        List<Session> unmatchedSessions = sessionService.findSessionsByDuration(30f);
        assertTrue(unmatchedSessions.isEmpty());
    }

    @Test
    void testFindSessionsByInstructorName() {
        InstructorAssignment mockAssignment = new InstructorAssignment();
        mockAssignment.setId(1);
        Session mockSession = new Session();
        mockSession.setId(1);
        when(instructorAssignmentRepository.findInstructorAssignmentByInstructorNameContainingIgnoreCase("Existing"))
                .thenReturn(Arrays.asList(mockAssignment));
        when(sessionRepository.findAll()).thenReturn(Arrays.asList(mockSession));
        when(sessionService.findAssignmentsForSession(mockSession)).thenReturn(Arrays.asList(mockAssignment));
        List<Session> sessions = sessionService.findSessionsByInstructorName("Existing");
        assertEquals(1, sessions.size());
        assertEquals(mockSession.getId(), sessions.get(0).getId());
    }

    @Test
    public void testCreateSession() throws Exception {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1);
        String courseName = "Yoga";
        String locationName = "Room 1";
        Session savedSession = new Session();
        savedSession.setStartTime(startTime);
        savedSession.setEndTime(endTime);
        when(courseRepository.findCourseByName(anyString())).thenReturn(null);
        when(locationRepository.findLocationByName(anyString())).thenReturn(null);
        when(sessionRepository.save(any(Session.class))).thenReturn(savedSession);
        Session result = sessionService.createSession(startTime, endTime, courseName, locationName);
        assertNotNull(result);
        assertEquals(startTime, result.getStartTime());
        assertEquals(endTime, result.getEndTime());
        verify(sessionRepository).save(any(Session.class));
    }

    @Test
    public void testDeleteSession() {
        Integer sessionId = 1;
        doNothing().when(sessionRepository).deleteById(sessionId);
        sessionService.deleteSession(sessionId);
        verify(sessionRepository).deleteById(sessionId);
    }

}
