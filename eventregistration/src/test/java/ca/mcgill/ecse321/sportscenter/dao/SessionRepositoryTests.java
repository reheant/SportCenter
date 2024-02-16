package ca.mcgill.ecse321.sportscenter.dao;

import java.sql.Time;
import ca.mcgill.ecse321.sportscenter.model.Course;
import ca.mcgill.ecse321.sportscenter.model.Location;
import ca.mcgill.ecse321.sportscenter.model.Session;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;




@SpringBootTest
public class SessionRepositoryTests {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private SessionRepository sessionRepository;

    @AfterEach
    public void clearDatabase() {
        courseRepository.deleteAll();
        sessionRepository.deleteAll();
        locationRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadSession() {
        // create a location

        String name = "Ana";
        int capacity = 44;

        Time openingTime = Time.valueOf("08:00:00");
        Time closingTime = Time.valueOf("12:00:00");

        Location location = new Location(name, capacity, openingTime, closingTime);
        locationRepository.save(location);

        // create a course
        String courseName = "boring class";
        String description = "this class is boring";
        boolean isApproved = true;
        boolean requiresInstructor = true;
        float duration = 60;
        float cost = 23;
        // load course
        Course course =
                new Course(courseName, description, isApproved, requiresInstructor, duration, cost);
        courseRepository.save(course);

        // create a session

        Time startTime = Time.valueOf("08:00:00");
        Time endTime = Time.valueOf("12:00:00");

        Session session = new Session(startTime, endTime, course, location);
        // load session
        sessionRepository.save(session);

        session = sessionRepository.findById(session.getId()).orElse(null);
        
        // Assert that session is not null and has correct attributes.
        assertNotNull(session);
        assertEquals(startTime, session.getStartTime());
        assertEquals(endTime, session.getEndTime());
        assertEquals(course.getId(), session.getCourse().getId());
        assertEquals(location.getId(), session.getLocation().getId());
        assertNotNull(session.getId());
        assertEquals(location.getCapacity(), session.getLocation().getCapacity());
        assertEquals(location.getClosingTime(), session.getLocation().getClosingTime());
        assertEquals(location.getOpeningTime(), session.getLocation().getOpeningTime());
        assertEquals(location.getName(), session.getLocation().getName());
        assertEquals(course.getName(), session.getCourse().getName());
        assertEquals(course.getCost(), session.getCourse().getCost());
        assertEquals(course.getDescription(), session.getCourse().getDescription());
        assertEquals(course.getIsApproved(), session.getCourse().getIsApproved());
        assertEquals(course.getRequiresInstructor(), session.getCourse().getRequiresInstructor());
        //assertEquals(course, session.getCourse());
        //assertEquals(location, session.getLocation());
        //assertEquals(location.getSessions(), session.getLocation().getSessions());
        // assertEquals(location.getSessions(), session);
    }

}
