package ca.mcgill.ecse321.sportscenter.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.sportscenter.model.*;
import ca.mcgill.ecse321.sportscenter.dao.*;


@ExtendWith(MockitoExtension.class)
public class TestScheduleService {
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private InstructorRepository instructorRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private InstructorAssignmentRepository instructorAssignmentRepository;
    
    @InjectMocks
    private ScheduleService service;

    private static final Integer COURSE_ID = 1;
    private static final Integer LOCATION_ID = 1;
    private static final Integer INSTRUCTOR_ID = 1;
    private static final String INSTRUCTOR_EMAIL = "gordonramsay@gmail.com";
    private static final String INSTRUCTOR_FIRST_NAME = "Gordon";
    private static final String INSTRUCTOR_LAST_NAME = "Ramsay";
    private static final String INSTRUCTOR_PASSWORD = "L4mbS8uce";
    private static final Integer INSTRUCTORASSIGNMENT_ID = 1;

    private Session session = new Session();
    private Course course;
    private Location location;
    private final Account instructorAccount = new Account();
    private final Instructor instructor = new Instructor();
    private InstructorAssignment instructorAssignment;

    @BeforeEach
    public void setMockOutput() {
        session = mock(Session.class);
        course = mock(Course.class);
        location = mock(Location.class);
        instructorAssignment = new InstructorAssignment();
        Session mySession = new Session();
        mySession.setId(session.getId());
        instructorAssignment.setSession(mySession);
        Instructor myIn = new Instructor();
        myIn.setId(INSTRUCTOR_ID);
        instructorAssignment.setInstructor(myIn);

        lenient().when(sessionRepository.findById(session.getId())).thenReturn(Optional.of(session));
        lenient().when(courseRepository.findById(COURSE_ID)).thenReturn(Optional.of(course));
        lenient().when(locationRepository.findById(LOCATION_ID)).thenReturn(Optional.of(location));
        lenient().when(instructorRepository.findById(INSTRUCTOR_ID)).thenReturn(Optional.of(instructor));
        lenient().when(instructorAssignmentRepository.findById(INSTRUCTORASSIGNMENT_ID)).thenReturn(Optional.of(instructorAssignment));
        lenient().when(instructorAssignmentRepository.save(any(InstructorAssignment.class))).thenReturn(instructorAssignment);
    
        lenient().when(instructorRepository.findInstructorByAccountEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(INSTRUCTOR_EMAIL)) {
                instructorAccount.setEmail(INSTRUCTOR_EMAIL);
                instructorAccount.setFirstName(INSTRUCTOR_FIRST_NAME);
                instructorAccount.setLastName(INSTRUCTOR_LAST_NAME);
                instructorAccount.setPassword(INSTRUCTOR_PASSWORD);
                instructor.setAccount(instructorAccount);

                return instructor;
            } else {
                return null;
            }
        });

        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(instructorAssignmentRepository.save(any(InstructorAssignment.class))).thenAnswer(returnParameterAsAnswer);
    }

    @Test
    public void testModifySessionTiming() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm:ss");
        LocalDateTime newStartTime = LocalDateTime.of(LocalDate.now(), LocalTime.parse("10:00:00", formatter));
        LocalDateTime newEndTime = LocalDateTime.of(LocalDate.now(), LocalTime.parse("12:00:00", formatter));
       
        Session updatedSession = null;
        try{
        when(session.getStartTime()).thenReturn(newStartTime);
        when(session.getEndTime()).thenReturn(newEndTime);

        updatedSession = service.modifySessionTime(session.getId(), newStartTime, newEndTime);

        verify(sessionRepository).save(any(Session.class));
        } catch (Exception error) {
            fail(error.getMessage());
        }
        assertNotNull(updatedSession);
        assertEquals(newStartTime, updatedSession.getStartTime());
        assertEquals(newEndTime, updatedSession.getEndTime());
    }

    @Test
    public void testModifySessionCourse() {
        Session updatedSession = null;
        try{
        when(session.getCourse()).thenReturn(course);
        when(course.getId()).thenReturn(COURSE_ID);

        updatedSession = service.modifySessionCourse(session.getId(), COURSE_ID);

        verify(sessionRepository).save(any(Session.class));
    } catch (Exception error) {
        fail(error.getMessage());
    }
        assertNotNull(updatedSession);
        assertEquals(COURSE_ID, updatedSession.getCourse().getId());
    }

    @Test
    public void testModifySessionLocation() {
        Session updatedSession = null;
        try{
        when(session.getLocation()).thenReturn(location);
        when(location.getId()).thenReturn(LOCATION_ID);

        updatedSession = service.modifySessionLocation(session.getId(), LOCATION_ID);

        verify(sessionRepository).save(any(Session.class));
        } catch (Exception error) {
        fail(error.getMessage());
        }
        assertNotNull(updatedSession);
        assertEquals(LOCATION_ID, updatedSession.getLocation().getId());
    }

    @Test
    public void testAssignInstructorToSession() {
        InstructorAssignment returnedAssignment = null;
        try{
            returnedAssignment = service.assignInstructorToSession(session.getId(), INSTRUCTOR_EMAIL);
        } catch (Exception error) {
            fail(error.getMessage());
        }
        assertNotNull(returnedAssignment);
        assertEquals(session.getId(), returnedAssignment.getSession().getId());
        assertEquals(INSTRUCTOR_EMAIL, returnedAssignment.getInstructor().getAccount().getEmail());
    }

}