package ca.mcgill.ecse321.sportscenter.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.sql.Time;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.sportscenter.model.*;
import ca.mcgill.ecse321.sportscenter.model.Course.CourseStatus;
import ca.mcgill.ecse321.sportscenter.service.ModifySportsCenterInformationService;
import ca.mcgill.ecse321.sportscenter.dao.*;
import ca.mcgill.ecse321.sportscenter.dto.*;

@ExtendWith(MockitoExtension.class)
public class TestModifySportsCenterInformation {

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private InstructorRepository instructorRepository;
    
    @InjectMocks
    private ModifySportsCenterInformationService service;

    private static final Integer COURSE_ID = 1;
    private static final Integer SESSION_ID = 1;
    private static final Integer LOCATION_ID = 1;
    private static final Integer INSTRUCTOR_ID = 1;

    @BeforeEach
    public void setMockOutput() {
        Course course = new Course();
        course.setId(COURSE_ID);
        course.setName("Pilates");
        lenient().when(courseRepository.findById(COURSE_ID)).thenReturn(Optional.of(course));
        lenient().when(courseRepository.save(any(Course.class))).thenAnswer(i -> i.getArguments()[0]);

        Session session = new Session();
        session.setId(SESSION_ID);
        Time startTime = Time.valueOf("09:00:00");
        Time endTime = Time.valueOf("10:30:00");
        session.setStartTime(startTime);
        session.setEndTime(endTime);
        lenient().when(sessionRepository.findById(SESSION_ID)).thenReturn(Optional.of(session));
        lenient().when(sessionRepository.save(any(Session.class))).thenAnswer(i -> i.getArguments()[0]);

        Location location = new Location();
        location.setId(LOCATION_ID);
        location.setName("Studio 1");
        location.setCapacity(100);
        lenient().when(locationRepository.findById(LOCATION_ID)).thenReturn(Optional.of(location));
        lenient(). when(locationRepository.save(any(Location.class))).thenAnswer(i -> i.getArguments()[0]);

        Instructor instructor = new Instructor();
        Account acc = new Account();
        instructor.setId(INSTRUCTOR_ID);
        instructor.setId(INSTRUCTOR_ID);
        acc.setFirstName("Tiffany");
        acc.setLastName("Miller");
        acc.setEmail("tiffy@gmail.com");
        acc.setPassword("abc123");
        instructor.setAccount(acc);
        lenient().when(instructorRepository.findById(INSTRUCTOR_ID)).thenReturn(Optional.of(instructor));
        lenient().when(instructorRepository.save(any(Instructor.class))).thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    public void testUpdateCourse() {
        //CourseDto courseDto = new CourseDto();
       // courseDto.setName("Yoga");
        Course updatedCourse = service.updateCenterCourse(COURSE_ID, "Yoga", 
        "hot yoga", CourseStatus.Approved, 13.0f, 1.0f);

        assertEquals("Yoga", updatedCourse.getName());
        assertEquals("hot yoga", updatedCourse.getDescription());
        assertEquals(CourseStatus.Approved, updatedCourse.getCourseStatus());
        assertEquals(13.0f, updatedCourse.getCost());
        assertEquals(1.0f, updatedCourse.getDefaultDuration());
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    public void testUpdateLocation() {
        //LocationDto locationDto = new LocationDto();
        Location updatedLocation = service.updateCenterLocation(LOCATION_ID, "Park", 
        100, Time.valueOf("09:00:00"), Time.valueOf("10:30:00"));

        assertEquals("Park", updatedLocation.getName());
        assertEquals(100, updatedLocation.getCapacity());
        verify(locationRepository).save(any(Location.class));
    }

    @Test
    public void testUpdateInstructor() {
        //InstructorDto instructorDto = new InstructorDto();
        //instructorDto.setName("Julien Audet");
        Instructor updatedInstructor = service.updateCenterInstructor(INSTRUCTOR_ID, 
        "Julien", "Audet", "jaudet@gmail.com");

        assertEquals("Julien", updatedInstructor.getAccount().getFirstName());
        assertEquals("Audet", updatedInstructor.getAccount().getLastName());
        assertEquals("jaudet@gmail.com", updatedInstructor.getAccount().getEmail());
        verify(instructorRepository).save(any(Instructor.class));
    }

}