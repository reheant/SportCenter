
package ca.mcgill.ecse321.sportscenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Course;
import ca.mcgill.ecse321.sportscenter.model.Instructor;
import ca.mcgill.ecse321.sportscenter.model.Location;
import ca.mcgill.ecse321.sportscenter.model.Session;
import ca.mcgill.ecse321.sportscenter.model.InstructorAssignment;
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
        sessionRepository.deleteAll();
        locationRepository.deleteAll();

    }

    @Test
    public void testPersistAndLoadInstructor() {
        // Create account.
        String firstName = "Muffin";
        String lastName = "Man";
        String email = "Man@gmail.com";
        String password = "123456";
        Account account = new Account(firstName, lastName, email, password);

        // Load account
        accountRepository.save(account);

        // Create instructor
        Instructor instructor = new Instructor(account);
        // Load customer
        instructorRepository.save(instructor);

        // create a course
        String courseName = "boring class";
        String description = "this class is boring";
        boolean isApproved = true;
        boolean requiresInstructor = true;
        float duration = 60;
        float cost = 23;

        Course course = new Course(courseName, description, isApproved, requiresInstructor, duration, cost);
        // Load Course
        courseRepository.save(course);

        // Create a location
        String name = "Ana";
        int capacity = 44;
        Time openingTime = Time.valueOf("08:00:00");
        Time closingTime = Time.valueOf("12:00:00");
        Location location = new Location(name, capacity, openingTime, closingTime);
        // Load Location
        locationRepository.save(location);

        // create a session
        Time startTime = Time.valueOf("08:00:00");
        Time endTime = Time.valueOf("12:00:00");
        Session session = new Session(startTime, endTime, course, location);
        // load session
        sessionRepository.save(session);

        // create instructorAssignment
        InstructorAssignment instructorAssignment = new InstructorAssignment(instructor, session);
        // Load instructorAssingment
        instructorAssignmentRepository.save(instructorAssignment);


        // Read account from database.
        InstructorAssignment dBinstructorAssignment = instructorAssignmentRepository.findById(instructorAssignment.getId()).orElse(null);
        // Assert that Instructor Assignmnent is not null and has correct attributes.
        assertNotNull(dBinstructorAssignment);
        assertEquals(dBinstructorAssignment.getId(), instructorAssignment.getId());
        assertEquals(dBinstructorAssignment.getInstructor().getId(), instructorAssignment.getInstructor().getId());
        assertEquals(dBinstructorAssignment.getSession().getId(), instructorAssignment.getSession().getId());
        assertEquals(course.getName(), dBinstructorAssignment.getSession().getCourse().getName());
        assertEquals(course.getCost(), dBinstructorAssignment.getSession().getCourse().getCost());
        assertEquals(course.getDescription(), dBinstructorAssignment.getSession().getCourse().getDescription());
        assertEquals(course.getIsApproved(), dBinstructorAssignment.getSession().getCourse().getIsApproved());
        assertEquals(course.getRequiresInstructor(), dBinstructorAssignment.getSession().getCourse().getRequiresInstructor());
        assertEquals(location.getClosingTime(),dBinstructorAssignment.getSession().getLocation().getClosingTime());
		assertEquals(location.getOpeningTime(),dBinstructorAssignment.getSession().getLocation().getOpeningTime());
		assertEquals(location.getId(), dBinstructorAssignment.getSession().getLocation().getId());
		assertEquals(location.getName(), dBinstructorAssignment.getSession().getLocation().getName());
		assertEquals(location.getCapacity(), dBinstructorAssignment.getSession().getLocation().getCapacity());
        assertEquals(session.getEndTime(), dBinstructorAssignment.getSession().getEndTime());
        assertEquals(session.getStartTime(), dBinstructorAssignment.getSession().getStartTime());
        assertEquals(instructorAssignment.getInstructor().getAccount().getEmail(), dBinstructorAssignment.getInstructor().getAccount().getEmail());
        assertEquals(instructorAssignment.getInstructor().getAccount().getFirstName(), dBinstructorAssignment.getInstructor().getAccount().getFirstName());
        assertEquals(instructorAssignment.getInstructor().getAccount().getLastName(), dBinstructorAssignment.getInstructor().getAccount().getLastName());
        assertEquals(instructorAssignment.getInstructor().getAccount().getPassword(), dBinstructorAssignment.getInstructor().getAccount().getPassword());
    }
}
