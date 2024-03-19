package ca.mcgill.ecse321.sportscenter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class ModifyScheduleRestControllerIntegrationTest {
    @Autowired
    private TestRestTemplate client;

    @Autowired
    private SessionRepository sessionRepo;
    @Autowired
    private CourseRepository courseRepo;
    @Autowired
    private LocationRepository locationRepo;
    @Autowired
    private InstructorRepository instructorRepo;
    @Autowired
    private InstructorAssignmentRepository instructorAssignmentRepo;
    @Autowired
    private AccountRepository accountRepo;

    @BeforeEach
    @AfterEach
    public void clearDb() {
        instructorAssignmentRepo.deleteAll();
        sessionRepo.deleteAll();
        courseRepo.deleteAll();
        locationRepo.deleteAll();
        instructorRepo.deleteAll();
    }

    private Session createDefaultSession(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm:ss");
        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.parse("9:00:00", formatter));
        LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.parse("10:00:00", formatter));
        Time openingTime = Time.valueOf("7:00:00");
        Time closingTime = Time.valueOf("18:00:00");
        Course course = new Course("Zumba", "fun zumba for all", CourseStatus.Approved, true, 1.0f, 20.0f);
        courseRepo.save(course);
        Location location = new Location("Studio A", 30, openingTime, closingTime);
        locationRepo.save(location);
        
        Session session = new Session(startTime, endTime, course, location);
        sessionRepo.save(session);
        return session;
    }

    @Test
    public void testModifySessionTime() {
        Session session = createDefaultSession();
        LocalDateTime newStartTime = LocalDateTime.of(LocalDate.now(), LocalTime.parse("10:00:00", DateTimeFormatter.ofPattern("HH:mm:ss")));
        LocalDateTime newEndTime = LocalDateTime.of(LocalDate.now(), LocalTime.parse("12:00:00", DateTimeFormatter.ofPattern("HH:mm:ss")));
        session.setEndTime(newEndTime);
        session.setStartTime(newStartTime);
        sessionRepo.save(session);

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
        locationRepo.save(newLocation);
        Session session = createDefaultSession();
        session.setLocation(newLocation);
        sessionRepo.save(session);

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
        courseRepo.save(newCourse);
        Session session = createDefaultSession();
        session.setCourse(newCourse);
        sessionRepo.save(session);

        String urlTemplate = UriComponentsBuilder.fromPath("/schedule/modify/sessions/" + session.getId()+ "/course")
        .queryParam("courseId", newCourse.getId())
        .encode()
        .toUriString();
        ResponseEntity<SessionDto> response = client.postForEntity(urlTemplate, session, SessionDto.class);

		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
    }

        @Test
        public void testAssignInstructorToSession() {
            Session session = createDefaultSession();
            Account acc = new Account("Alex", "Whaba", "awhaba@gmail.com", "aBc123!");
            accountRepo.save(acc);
            Instructor instructor = new Instructor(acc);
            instructorRepo.save(instructor);
            InstructorAssignment ass = new InstructorAssignment(instructor, session);
            instructorAssignmentRepo.save(ass);

            String urlTemplate = UriComponentsBuilder.fromPath("/schedule/modify/sessions/" + session.getId()+ "/instructor")
            .queryParam("instructorId", instructor.getId())
            .encode()
            .toUriString();
            ResponseEntity<InstructorAssignmentDto> response = client.postForEntity(urlTemplate, ass, InstructorAssignmentDto.class);

            assertNotNull(response);
            assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
    }

}
