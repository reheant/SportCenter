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
import ca.mcgill.ecse321.sportscenter.dao.CourseRepository;
import ca.mcgill.ecse321.sportscenter.dao.LocationRepository;
import ca.mcgill.ecse321.sportscenter.dao.SessionRepository;

	
	
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
        //create a location
        int locationID = 4 ;
        String name =  "Ana" ;
        int capacity = 44;
        
        Time openingTime = Time.valueOf("08:00:00");
		Time closingTime = Time.valueOf("12:00:00");

        Location location = new Location(locationID, name, capacity, openingTime, closingTime);
        locationRepository.save(location);

		//create a course
        int courseID = 23;
        String courseName = "boring class";
        String description = "this class is boring";
        boolean isApproved = true;
        boolean requiresInstructor = true;
        float duration = 60;
        float cost = 23;  
		//load course
        Course course = new Course(courseID, courseName, description, isApproved, requiresInstructor, duration, cost);
        courseRepository.save(course);

        //create a session
        int sessionID = 3;
        Time startTime = Time.valueOf("08:00:00");
		Time endTime = Time.valueOf("12:00:00");

        Session session = new Session(sessionID, startTime, endTime, course, location);
        // load session
        sessionRepository.save(session);

        session = sessionRepository.findById(sessionID).orElse(null);
        assertNotNull(session);
		assertNotNull(session.getId());
		assertEquals(startTime, session.getStartTime());
        assertEquals(endTime, session.getEndTime());
        assertEquals(course, session.getCourse());
        assertEquals(location, session.getLocation());
        assertEquals(location.getCapacity(), session.getLocation().getCapacity());
        assertEquals(location.getClosingTime(), session.getLocation().getClosingTime());
        assertEquals(location.getOpeningTime(), session.getLocation().getOpeningTime());
        assertEquals(location.getSessions(), session);
        assertEquals(location.getId(), session.getLocation().getId());
        assertEquals(location.getName(), session.getLocation().getName());
		assertEquals(course.getName(), session.getCourse().getName());
		assertEquals(course.getCost(), session.getCourse().getCost());
		assertEquals(course.getId(), session.getCourse().getId());
		assertEquals(course.getDescription(), session.getCourse().getDescription());
		assertEquals(course.getIsApproved(), session.getCourse().getIsApproved());
		assertEquals(course.getName(), session.getCourse().getName());
		assertEquals(course.getRequiresInstructor(), session.getCourse().getRequiresInstructor());
		assertEquals(location.getClosingTime(), session.getLocation().getClosingTime());
		assertEquals(location.getOpeningTime(), session.getLocation().getOpeningTime());
		assertEquals(location.getSessions(), session.getLocation().getSessions());
		assertEquals(location.getId(), session.getLocation().getId());
		assertEquals(location.getName(), session.getLocation().getName());
		assertEquals(location.getCapacity(), session.getLocation().getCapacity());
    }

}
