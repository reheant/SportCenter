package ca.mcgill.ecse321.sportscenter;

import java.sql.Time;
import ca.mcgill.ecse321.sportscenter.model.Course;
import ca.mcgill.ecse321.sportscenter.model.Location;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.sportscenter.dao.CourseRepository;
import ca.mcgill.ecse321.sportscenter.dao.LocationRepository;


	
	
@SpringBootTest
public class CourseRepositoryTests {

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private LocationRepository locationRepository;
	
	@AfterEach
	public void clearDatabase() {
		courseRepository.deleteAll();

		locationRepository.deleteAll();
	}

    @Test
	public void testPersistAndLoadCourse() {
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


        course = courseRepository.findById(courseID).orElse(null);
        assertNotNull(course);
		assertNotNull(course.getId());
        assertEquals(location.getCapacity(), location.getCapacity());
        assertEquals(location.getClosingTime(), location.getClosingTime());
        assertEquals(location.getOpeningTime(), location.getOpeningTime());
        assertEquals(location.getId(), location.getId());
        assertEquals(location.getName(), location.getName());
		assertEquals(course.getName(), course.getName());
		assertEquals(course.getCost(), course.getCost());
		assertEquals(course.getId(), course.getId());
		assertEquals(course.getDescription(), course.getDescription());
		assertEquals(course.getIsApproved(), course.getIsApproved());
		assertEquals(course.getName(), course.getName());
		assertEquals(course.getRequiresInstructor(), course.getRequiresInstructor());
		assertEquals(location.getClosingTime(), location.getClosingTime());
		assertEquals(location.getOpeningTime(), location.getOpeningTime());
		assertEquals(location.getSessions(), location.getSessions());
		assertEquals(location.getId(), location.getId());
		assertEquals(location.getName(), location.getName());
		assertEquals(location.getCapacity(), location.getCapacity());
    }
}
