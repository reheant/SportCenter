package ca.mcgill.ecse321.sportscenter.dao;

import java.sql.Time;
import ca.mcgill.ecse321.sportscenter.model.Course;
import ca.mcgill.ecse321.sportscenter.model.Session;
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
	}

	@Test
	public void testPersistAndLoadCourse() {
		// create a location
		// String name = "Ana";
		// int capacity = 44;

		// Time openingTime = Time.valueOf("08:00:00");
		// Time closingTime = Time.valueOf("12:00:00");

		// Location location = new Location(name, capacity, openingTime, closingTime);
		// locationRepository.save(location);

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


		course = courseRepository.findById(course.getId()).orElse(null);

		// assertNotNull(course);
		// assertEquals(location, course.getSessions().get(0).getLocation());
		// assertEquals(location.getCapacity(),
		// course.getSessions().get(0).getLocation().getCapacity());
		// assertEquals(location.getClosingTime(),
		// course.getSessions().get(0).getLocation().getClosingTime());
		// assertEquals(location.getOpeningTime(),course.getSessions().get(0).getLocation().getOpeningTime());
		// assertEquals(location.getName(), course.getSessions().get(0).getLocation().getName());
		// assertEquals(location.getClass(), course.getSessions().get(0).getLocation().getClass());

		assertEquals(courseName, course.getName());
		assertEquals(cost, course.getCost());
		assertEquals(description, course.getDescription());
		assertEquals(isApproved, course.getIsApproved());
		assertEquals(requiresInstructor, course.getRequiresInstructor());
		assertEquals(duration, course.getDefaultDuration());

	}
}
