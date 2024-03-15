package ca.mcgill.ecse321.sportscenter.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Time;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import ca.mcgill.ecse321.sportscenter.model.*;
import ca.mcgill.ecse321.sportscenter.dao.*;


@ExtendWith(MockitoExtension.class)
public class TestModifyScheduleService {
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private InstructorRepository instructorRepository;
    @Mock
    private InstructorAssignmentRepository instructorAssignmentRepository;
    
    @InjectMocks
    private ModifyScheduleService service;

    private static final Integer SESSION_ID = 1;
    private static final Integer COURSE_ID = 1;
    private static final Integer LOCATION_ID = 1;
    private static final Integer INSTRUCTOR_ID = 1;
    private static final Integer INSTRUCTORASSIGNMENT_ID = 1;

    private Session session;
    private Course course;
    private Location location;
    private Instructor instructor;
    private InstructorAssignment instructorAssignment;

    @BeforeEach
    public void setMockOutput() {
        session = mock(Session.class);
        course = mock(Course.class);
        location = mock(Location.class);
        instructor = mock(Instructor.class);
        instructorAssignment = new InstructorAssignment();
        Session mySession = new Session();
        mySession.setId(SESSION_ID);
        instructorAssignment.setSession(mySession);
        Instructor myIn = new Instructor();
        myIn.setId(INSTRUCTOR_ID);
        instructorAssignment.setInstructor(myIn);

        lenient().when(sessionRepository.findById(SESSION_ID)).thenReturn(Optional.of(session));
        lenient().when(courseRepository.findById(COURSE_ID)).thenReturn(Optional.of(course));
        lenient().when(locationRepository.findById(LOCATION_ID)).thenReturn(Optional.of(location));
        lenient().when(instructorRepository.findById(INSTRUCTOR_ID)).thenReturn(Optional.of(instructor));
        lenient().when(instructorAssignmentRepository.findById(INSTRUCTORASSIGNMENT_ID)).thenReturn(Optional.of(instructorAssignment));
        lenient().when(instructorAssignmentRepository.save(any(InstructorAssignment.class))).thenReturn(instructorAssignment);
    }

    @Test
    public void testModifySessionTiming() {
        Time newStartTime = Time.valueOf("10:00:00");
        Time newEndTime = Time.valueOf("12:00:00");
        when(session.getStartTime()).thenReturn(newStartTime);
        when(session.getEndTime()).thenReturn(newEndTime);

        Session updatedSession = service.modifySessionTime(SESSION_ID, newStartTime, newEndTime);

        verify(sessionRepository).save(any(Session.class));
        assertEquals(newStartTime, updatedSession.getStartTime());
        assertEquals(newEndTime, updatedSession.getEndTime());
    }

    @Test
    public void testModifySessionCourse() {
        when(session.getCourse()).thenReturn(course);
        when(course.getId()).thenReturn(COURSE_ID);

        Session updatedSession = service.modifySessionCourse(SESSION_ID, COURSE_ID);

        verify(sessionRepository).save(any(Session.class));
        assertEquals(COURSE_ID, updatedSession.getCourse().getId());
    }

    @Test
    public void testModifySessionLocation() {
        when(session.getLocation()).thenReturn(location);
        when(location.getId()).thenReturn(LOCATION_ID);

        Session updatedSession = service.modifySessionLocation(SESSION_ID, LOCATION_ID);

        verify(sessionRepository).save(any(Session.class));
        assertEquals(LOCATION_ID, updatedSession.getLocation().getId());
    }

    @Test
    public void testAssignInstructorToSession() {
        InstructorAssignment returnedAssignment = service.assignInstructorToSession(SESSION_ID, INSTRUCTOR_ID);

        assertNotNull(returnedAssignment);
        assertEquals(SESSION_ID, returnedAssignment.getSession().getId());
        assertEquals(INSTRUCTOR_ID, returnedAssignment.getInstructor().getId());
    }

}