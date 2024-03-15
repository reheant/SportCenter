
package ca.mcgill.ecse321.sportscenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Course;
import ca.mcgill.ecse321.sportscenter.model.Course.CourseStatus;
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
        instructorAssignmentRepository.deleteAll();
        sessionRepository.deleteAll();
        locationRepository.deleteAll();
        instructorRepository.deleteAll();
        accountRepository.deleteAll();

    }

    @Test
    public void testPersistAndLoadInstructor() {
        // Create account.
        String firstName = "Muffin";
        String lastName = "Man";
        String email = "Man@gmail.com";
        String password = "123456";
        Account account = new Account(firstName, lastName, email, password);

        // Save account
        accountRepository.save(account);

        // Create instructor
        Instructor instructor = new Instructor(account);
        // Save customer
        instructorRepository.save(instructor);

        // create a course

        String courseName = "boring class";
        String description = "this class is boring";
        CourseStatus courseStatus = CourseStatus.Approved;
        boolean requiresInstructor = true;
        float duration = 60;
        float cost = 23;

        Course course = new Course(courseName, description, courseStatus, requiresInstructor,
                duration, cost);
        courseRepository.save(course);

        // create a location
        String name = "Ana";
        int capacity = 44;

        Time openingTime = Time.valueOf("08:00:00");
        Time closingTime = Time.valueOf("12:00:00");

        Location location = new Location(name, capacity, openingTime, closingTime);
        locationRepository.save(location);

        // create a session
        Time startTime = Time.valueOf("08:00:00");
        Time endTime = Time.valueOf("12:00:00");

        Session session = new Session(startTime, endTime, course, location);
        // load session
        sessionRepository.save(session);

        // create instructorAssignment
        InstructorAssignment instructorAssignment = new InstructorAssignment(instructor, session);
        instructorAssignmentRepository.save(instructorAssignment);

        // Read account from database.
        InstructorAssignment dBinstructorAssignment = instructorAssignmentRepository
                .findById(instructorAssignment.getId()).orElse(null);
        // Assert that account is not null and has correct attributes.
        assertNotNull(dBinstructorAssignment);
        assertEquals(dBinstructorAssignment.getId(), instructorAssignment.getId());
        assertEquals(dBinstructorAssignment.getInstructor().getId(),
                instructorAssignment.getInstructor().getId());
        assertEquals(dBinstructorAssignment.getSession().getId(),
                instructorAssignment.getSession().getId());
    }
}
