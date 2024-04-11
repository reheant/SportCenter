package ca.mcgill.ecse321.sportscenter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import ca.mcgill.ecse321.sportscenter.dao.*;
import ca.mcgill.ecse321.sportscenter.dto.*;
import ca.mcgill.ecse321.sportscenter.model.*;
import ca.mcgill.ecse321.sportscenter.model.Course.CourseStatus;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ScheduleRestControllerIntegrationTest {
    @Autowired
    private TestRestTemplate client;

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private InstructorAssignmentRepository instructorAssignmentRepository;
    @Autowired
    private AccountRepository accountRepository;

    private String email = "jimbob@gmail.com";
    private Account account = new Account();
    private Instructor instructor = new Instructor();
    private Location location = new Location();
    private Course course = new Course();
    private Session session = new Session();
    private InstructorAssignment instructorAssignment = new InstructorAssignment();

    @BeforeEach
    @AfterEach
    public void clearDb() {
        instructorAssignmentRepository.deleteAll();
        sessionRepository.deleteAll();
        courseRepository.deleteAll();
        locationRepository.deleteAll();
        instructorRepository.deleteAll();
    }

    private Session createDefaultSession(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm:ss");
        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.parse("9:00:00", formatter));
        LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.parse("10:00:00", formatter));
        Time openingTime = Time.valueOf("7:00:00");
        Time closingTime = Time.valueOf("18:00:00");
        Course course = new Course("Zumba", "fun zumba for all", CourseStatus.Approved, true, 1.0f, 20.0f);
        courseRepository.save(course);
        Location location = new Location("Studio A", 30, openingTime, closingTime);
        locationRepository.save(location);
        
        Session session = new Session(startTime, endTime, course, location);
        sessionRepository.save(session);
        return session;
    }

    @Test
    public void testModifySessionTime() {
        Session session = createDefaultSession();
        LocalDateTime newStartTime = LocalDateTime.of(LocalDate.now(), LocalTime.parse("10:00:00", DateTimeFormatter.ofPattern("HH:mm:ss")));
        LocalDateTime newEndTime = LocalDateTime.of(LocalDate.now(), LocalTime.parse("12:00:00", DateTimeFormatter.ofPattern("HH:mm:ss")));
        session.setEndTime(newEndTime);
        session.setStartTime(newStartTime);
        sessionRepository.save(session);

        String urlTemplate = UriComponentsBuilder.fromPath("/schedule/modify/sessions/" + session.getId()+ "/time")
        .queryParam("startTime", newStartTime)
        .queryParam("endTime", newEndTime)
        .encode()
        .toUriString();
        ResponseEntity<SessionDto> response = client.postForEntity(urlTemplate, session, SessionDto.class);

		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
    }

    @Test
    public void testModifySessionLocation() {
        Location newLocation = new Location("gym", 50, Time.valueOf("5:00:00"), Time.valueOf("23:00:00"));
        locationRepository.save(newLocation);
        Session session = createDefaultSession();
        session.setLocation(newLocation);
        sessionRepository.save(session);

        String urlTemplate = UriComponentsBuilder.fromPath("/schedule/modify/sessions/" + session.getId()+ "/location")
        .queryParam("locationId", newLocation.getId())
        .encode()
        .toUriString();
        ResponseEntity<SessionDto> response = client.postForEntity(urlTemplate, session, SessionDto.class);

		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
    }
    @Test
    public void testModifySessionCourse() {
        Course newCourse = new Course("Zumba", "fun zumba for all", CourseStatus.Approved, true, 1.0f, 20.0f);     
        courseRepository.save(newCourse);
        Session session = createDefaultSession();
        session.setCourse(newCourse);
        sessionRepository.save(session);

        String urlTemplate = UriComponentsBuilder.fromPath("/schedule/modify/sessions/" + session.getId()+ "/course")
        .queryParam("courseId", newCourse.getId())
        .encode()
        .toUriString();
        ResponseEntity<SessionDto> response = client.postForEntity(urlTemplate, session, SessionDto.class);

		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
    }

    @Test
    public void testAssignInstructorToSessionSucceeds() {
        createAndSaveClassesForRegistration();
        String urlTemplate = UriComponentsBuilder.fromPath("/schedule/modify/sessions/" + session.getId() + "/instructor")
            .queryParam("instructorAccountEmail", instructor.getAccount().getEmail())
            .encode()
            .toUriString();
        ResponseEntity<InstructorAssignmentDto> postResponse = client.postForEntity(urlTemplate, null, InstructorAssignmentDto.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
    }

    @Test
    public void testUnassignInstructorToSessionSucceeds() {
        createTestInstructorAssignment();
        assertTrue(instructorAssignmentRepository.findById(instructorAssignment.getId()).isPresent());
        String urlTemplate = UriComponentsBuilder.fromPath("/schedule/modify/sessions/" + session.getId() + "/instructor")
            .queryParam("instructorAccountEmail", instructor.getAccount().getEmail())
            .encode()
            .toUriString();
        client.delete(urlTemplate);
        assertFalse(instructorAssignmentRepository.findById(instructorAssignment.getId()).isPresent());
    }

    private void createTestInstructorAssignment() {
        createAndSaveClassesForRegistration();
        instructorAssignment.setInstructor(instructor);
        instructorAssignment.setSession(session);
        instructorAssignmentRepository.save(instructorAssignment);
    }

    private void createAndSaveClassesForRegistration() {
        course.setName("Sample-Course");
        courseRepository.save(course);

        location.setName("Sample-Location");
        locationRepository.save(location);

        session.setCourse(course);
        session.setLocation(location);
        sessionRepository.save(session);

        account.setEmail(email);
        accountRepository.save(account);

        instructor.setAccount(account);
        instructorRepository.save(instructor);
    }
}
