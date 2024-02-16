package ca.mcgill.ecse321.sportscenter.dao;


import ca.mcgill.ecse321.sportscenter.model.Course;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

		// create a course
		String courseName = "boring class";
		String description = "this class is boring";
		boolean isApproved = true;
		boolean requiresInstructor = true;
		float duration = 60;
		float cost = 23;
		Course course = new Course(courseName, description, isApproved, requiresInstructor, duration, cost);
		// load course
		courseRepository.save(course);

		course = courseRepository.findById(course.getId()).orElse(null);
		// Assert that course is not null and has correct attributes.
		assertEquals(courseName, course.getName());
		assertEquals(cost, course.getCost());
		assertEquals(description, course.getDescription());
		assertEquals(isApproved, course.getIsApproved());
		assertEquals(requiresInstructor, course.getRequiresInstructor());
		assertEquals(duration, course.getDefaultDuration());

	}
}
