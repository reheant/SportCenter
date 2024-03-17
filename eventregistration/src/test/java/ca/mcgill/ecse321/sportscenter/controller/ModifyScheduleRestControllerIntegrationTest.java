package ca.mcgill.ecse321.sportscenter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    @BeforeEach
    @AfterEach
    public void clearDb() {
        sessionRepo.deleteAll();
        courseRepo.deleteAll();
        locationRepo.deleteAll();
        instructorRepo.deleteAll();
        instructorAssignmentRepo.deleteAll();
    }

    @Test
    public void testModifySessionTime() {
        Course c = new Course("yoga", "hot yoga", CourseStatus.Approved, true, 1.0f, 13.0f);
        Location l = new Location("park", 40, Time.valueOf("6:00:00"), Time.valueOf("18:00:00"));
        Session s = new Session (Time.valueOf("8:00:00"), Time.valueOf("9:00:00"), c, l);

        Time startTime = Time.valueOf("10:00:00");
        Time endTime = Time.valueOf("12:00:00");
        Integer sessionId = s.getId();

        ResponseEntity<SessionDto> response = client.exchange(
            "/schedule/modify/sessions/{sessionId}/time?startTime={startTime}&endTime={endTime}",
            HttpMethod.PUT,
            null,
            SessionDto.class,
            sessionId,
            startTime,
            endTime
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
    @Test
    public void testModifySessionTimeBadRequest() {
    
    Integer sessionId = -1; //no such session id
    Time startTime = Time.valueOf("10:00:00");
    Time endTime = Time.valueOf("12:00:00");

    ResponseEntity<String> response = client.exchange(
        "/schedule/modify/sessions/{sessionId}/time?startTime={startTime}&endTime={endTime}",
        HttpMethod.PUT,
        null,
        String.class,
        sessionId,
        startTime,
        endTime
    );
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testModifySessionLocation() {
    Location newLocation = new Location("gym", 50, Time.valueOf("5:00:00"), Time.valueOf("23:00:00"));
    Session session = new Session(Time.valueOf("10:00:00"), Time.valueOf("11:00:00"), new Course(), newLocation);
    Integer sessionId = session.getId();
    Integer locationId = newLocation.getId(); 

    ResponseEntity<SessionDto> response = client.exchange(
        "/schedule/modify/sessions/{sessionId}/location/{locationId}",
        HttpMethod.PUT,
        null,
        SessionDto.class,
        sessionId,
        locationId
    );

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    }

    @Test
    public void testModifySessionLocationBadRequest() {
        Integer sessionId = -1; // Id no exist
        Integer locationId = -1; // Id no exist

        ResponseEntity<String> response = client.exchange(
            "/schedule/modify/sessions/{sessionId}/location/{locationId}",
            HttpMethod.PUT,
            null,
            String.class,
            sessionId,
            locationId
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }

        @Test
        public void testAssignInstructorToSession() {
        Instructor instructor = new Instructor();
        Session session = new Session(Time.valueOf("12:00:00"), Time.valueOf("13:00:00"), new Course(), new Location());
        Integer sessionId = session.getId();
        Integer instructorId = instructor.getId(); 

        ResponseEntity<InstructorAssignmentDto> response = client.exchange(
            "/schedule/modify/sessions/{sessionId}/instructor/{instructorId}",
            HttpMethod.PUT,
            null,
            InstructorAssignmentDto.class,
            sessionId,
            instructorId
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testAssignInstructorToSessionBadRequest() {
        Integer sessionId = -1; 
        Integer instructorId = -1;

        ResponseEntity<String> response = client.exchange(
            "/schedule/modify/sessions/{sessionId}/instructor/{instructorId}",
            HttpMethod.PUT,
            null,
            String.class,
            sessionId,
            instructorId
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }
   
}
