package ca.mcgill.ecse321.sportscenter.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
    private ScheduleService scheduleService;

    private static final String INSTRUCTOR_EMAIL = "gordonramsay@gmail.com";
    private static final String INSTRUCTOR_FIRST_NAME = "Gordon";
    private static final String INSTRUCTOR_LAST_NAME = "Ramsay";
    private static final String INSTRUCTOR_PASSWORD = "L4mbS8uce";


    private final Session session = new Session();
    private final Course course = new Course();
    private final Location location = new Location();
    private final Account instructorAccount = new Account();
    private final Instructor instructor = new Instructor();
    private final InstructorAssignment instructorAssignment = new InstructorAssignment();

    @BeforeEach
    public void setMockOutput() {
        instructorAccount.setEmail(INSTRUCTOR_EMAIL);
        instructor.setAccount(instructorAccount);

        instructorAssignment.setSession(session);
        instructorAssignment.setInstructor(instructor);

        lenient().when(sessionRepository.findById(session.getId())).thenReturn(Optional.of(session));
        lenient().when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        lenient().when(locationRepository.findById(location.getId())).thenReturn(Optional.of(location));
        lenient().when(instructorRepository.findById(instructor.getId())).thenReturn(Optional.of(instructor));
        lenient().when(instructorAssignmentRepository.findById(instructorAssignment.getId())).thenReturn(Optional.of(instructorAssignment));
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

        updatedSession = scheduleService.modifySessionTime(session.getId(), newStartTime, newEndTime);

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
        updatedSession = scheduleService.modifySessionCourse(session.getId(), course.getId());

        verify(sessionRepository).save(any(Session.class));
    } catch (Exception error) {
        fail(error.getMessage());
    }
        assertNotNull(updatedSession);
        assertEquals(course.getId(), updatedSession.getCourse().getId());
    }

    @Test
    public void testModifySessionLocation() {
        Session updatedSession = null;
        try{
        updatedSession = scheduleService.modifySessionLocation(session.getId(), location.getId());

        verify(sessionRepository).save(any(Session.class));
        } catch (Exception error) {
        fail(error.getMessage());
        }
        assertNotNull(updatedSession);
        assertEquals(location.getId(), updatedSession.getLocation().getId());
    }

    @Test
    public void testAssignInstructorToSession() {
        InstructorAssignment returnedAssignment = null;
        try{
            returnedAssignment = scheduleService.assignInstructorToSession(session.getId(), INSTRUCTOR_EMAIL);
        } catch (Exception error) {
            fail(error.getMessage());
        }
        assertNotNull(returnedAssignment);
        assertEquals(session.getId(), returnedAssignment.getSession().getId());
        assertEquals(INSTRUCTOR_EMAIL, returnedAssignment.getInstructor().getAccount().getEmail());
    }

    @Test
    public void testUnassignInstructorFromSessionSuccessful() {

        lenient().when(instructorAssignmentRepository.findInstructorAssignmentByInstructorAccountEmailAndSessionId(anyString(), anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(INSTRUCTOR_EMAIL) && invocation.getArgument(1).equals(session.getId())) {
                instructorAccount.setEmail(INSTRUCTOR_EMAIL);
                instructorAccount.setFirstName(INSTRUCTOR_FIRST_NAME);
                instructorAccount.setLastName(INSTRUCTOR_LAST_NAME);
                instructorAccount.setPassword(INSTRUCTOR_PASSWORD);
                instructor.setAccount(instructorAccount);

                instructorAssignment.setInstructor(instructor);
                instructorAssignment.setSession(session);

                return instructorAssignment;
            } else {
                return null;
            }
        });

        try {            
            Boolean result = scheduleService.unassignInstructorFromSession(session.getId(), instructor.getAccount().getEmail());
            assertTrue(result);
        } catch (Exception error) {
            fail(error.getMessage());
        }
    }

    @Test
    public void testUnassignInstructorFromSessionNullSessionIdFails() {
        lenient().when(instructorAssignmentRepository.findInstructorAssignmentByInstructorAccountEmailAndSessionId(anyString(), anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(INSTRUCTOR_EMAIL) && invocation.getArgument(1).equals(session.getId())) {
                instructorAccount.setEmail(INSTRUCTOR_EMAIL);
                instructorAccount.setFirstName(INSTRUCTOR_FIRST_NAME);
                instructorAccount.setLastName(INSTRUCTOR_LAST_NAME);
                instructorAccount.setPassword(INSTRUCTOR_PASSWORD);
                instructor.setAccount(instructorAccount);

                instructorAssignment.setInstructor(instructor);
                instructorAssignment.setSession(session);

                return instructorAssignment;
            } else {
                return null;
            }
        });

        try {            
            scheduleService.unassignInstructorFromSession(null, instructor.getAccount().getEmail());
        } catch (Exception error) {
            assertEquals("The session id cannot be null.", error.getMessage());
            assertEquals(NullPointerException.class, error.getClass());
        }
    }

    @Test
    public void testUnassignInstructorFromSessionNullInstructorAccountEmailFails() {
        lenient().when(instructorAssignmentRepository.findInstructorAssignmentByInstructorAccountEmailAndSessionId(anyString(), anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(INSTRUCTOR_EMAIL) && invocation.getArgument(1).equals(session.getId())) {
                instructorAccount.setEmail(INSTRUCTOR_EMAIL);
                instructorAccount.setFirstName(INSTRUCTOR_FIRST_NAME);
                instructorAccount.setLastName(INSTRUCTOR_LAST_NAME);
                instructorAccount.setPassword(INSTRUCTOR_PASSWORD);
                instructor.setAccount(instructorAccount);

                instructorAssignment.setInstructor(instructor);
                instructorAssignment.setSession(session);

                return instructorAssignment;
            } else {
                return null;
            }
        });

        try {            
            scheduleService.unassignInstructorFromSession(session.getId(), null);
        } catch (Exception error) {
            assertEquals("The instructor account email cannot be null.", error.getMessage());
            assertEquals(NullPointerException.class, error.getClass());
        }
    }

    @Test
    public void testUnassignInstructorFromSessionAssignmentNotFoundFails() {
        lenient().when(instructorAssignmentRepository.findInstructorAssignmentByInstructorAccountEmailAndSessionId(anyString(), anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(INSTRUCTOR_EMAIL) && invocation.getArgument(1).equals(session.getId())) {
                instructorAccount.setEmail(INSTRUCTOR_EMAIL);
                instructorAccount.setFirstName(INSTRUCTOR_FIRST_NAME);
                instructorAccount.setLastName(INSTRUCTOR_LAST_NAME);
                instructorAccount.setPassword(INSTRUCTOR_PASSWORD);
                instructor.setAccount(instructorAccount);

                instructorAssignment.setInstructor(instructor);
                instructorAssignment.setSession(session);

                return instructorAssignment;
            } else {
                return null;
            }
        });

        try {            
            scheduleService.unassignInstructorFromSession(76, "randomPerson@whoStillUsesYahooAnymore.com");
        } catch (Exception error) {
            assertEquals("No matching instructor assignment.", error.getMessage());
            assertEquals(IllegalArgumentException.class, error.getClass());
        }
    }
}