
package ca.mcgill.ecse321.sportscenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportscenter.dao.CourseRepository;
import ca.mcgill.ecse321.sportscenter.dao.InstructorAssignmentRepository;
import ca.mcgill.ecse321.sportscenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportscenter.dao.LocationRepository;
import ca.mcgill.ecse321.sportscenter.dao.SessionRepository;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Course;
import ca.mcgill.ecse321.sportscenter.model.Instructor;
import ca.mcgill.ecse321.sportscenter.model.Location;
import ca.mcgill.ecse321.sportscenter.model.Session;
import ca.mcgill.ecse321.sportscenter.model.InstructorAssignment;

import java.sql.Date;
import java.sql.Time;

@SpringBootTest
public class InstructorAssignmentRepositoryTests {
    
	@Autowired
	private AccountRepository accountRepository;
    @Autowired
	private InstructorRepository instructorRepository;
    @Autowired
    private InstructorAssignmentRepository instructorAssignmentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private LocationRepository locationRepository;

	@AfterEach
	public void clearDatabase() {
		accountRepository.deleteAll();
        instructorRepository.deleteAll();
        instructorAssignmentRepository.deleteAll();
        locationRepository.deleteAll();
        sessionRepository.deleteAll();
        
	}

	@Test
	public void testPersistAndLoadInstructor() {
		// Create account.
		int id = 1;
		String firstName = "Muffin";
		String lastName = "Man";
		String email = "Man@gmail.com";
		String password = "123456";
		Account account = new Account(id, firstName, lastName, email, password);
		

		// Save account
		accountRepository.save(account);

        //Create instructor
        int instructorID = 3; 
        Instructor instructor = new Instructor(instructorID, account);
        // Save customer
        instructorRepository.save(instructor);

        //create a course

        int courseID = 23;
        String courseName = "boring class";
        String description = "this class is boring";
        boolean isApproved = true;
        boolean requiresInstructor = true;
        float duration = 60;
        float cost = 23;  

        Course course = new Course(courseID, courseName, description, isApproved, requiresInstructor, duration, cost);
        courseRepository.save(course);

        //create a location
        int locationID = 4 ;
        String name =  "Ana" ;
        int capacity = 44;
        
        Time openingTime = Time.valueOf("08:00:00");
		Time closingTime = Time.valueOf("12:00:00");

        Location location = new Location(locationID, name, capacity, openingTime, closingTime);
        locationRepository.save(location);

        //create a session
        int sessionID = 3;
        Time startTime = Time.valueOf("08:00:00");
		Time endTime = Time.valueOf("12:00:00");

        Session session = new Session(sessionID, startTime, endTime, course, location);
        // load session
        sessionRepository.save(session);

        // create instructorAssignment
        int assignmentID = 182;
        InstructorAssignment instructorAssignment = new InstructorAssignment(assignmentID, instructor, session);
        instructorAssignmentRepository.save(instructorAssignment);


		// Read account from database.
		instructorAssignment = instructorAssignmentRepository.findById(id).orElse(null);

		// Assert that account is not null and has correct attributes.
		assertNotNull(instructorAssignment);
		assertEquals(assignmentID, instructorAssignment.getId());
        assertEquals(instructor, instructorAssignment.getInstructor());
        assertEquals(session, instructorAssignment.getSession());
	}
    
}