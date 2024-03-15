package ca.mcgill.ecse321.sportscenter.service;

import ca.mcgill.ecse321.sportscenter.dao.CourseRepository;
import ca.mcgill.ecse321.sportscenter.dao.LocationRepository;
import ca.mcgill.ecse321.sportscenter.dao.SessionRepository;
import ca.mcgill.ecse321.sportscenter.dto.SessionDto;
import ca.mcgill.ecse321.sportscenter.model.Course;
import ca.mcgill.ecse321.sportscenter.model.Location;
import ca.mcgill.ecse321.sportscenter.model.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SessionIntegrationTests {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private LocationRepository locationRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        sessionRepository.deleteAll();
    }

    @Test
    public void testGetAllSessions() throws Exception {
        LocalDateTime startTime = LocalDateTime.now().plusDays(1);
        LocalDateTime endTime = startTime.plusHours(1);
        createTestSession("Existing Course", "Existing Location", startTime, endTime);
        ResponseEntity<SessionDto[]> response = client.getForEntity("/sessions", SessionDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length >= 1);
    }

    @Test
    public void testViewFilteredSessions() throws Exception {
        LocalDateTime startTime = LocalDateTime.now().plusDays(2).truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime endTime = startTime.plusHours(1).truncatedTo(ChronoUnit.SECONDS);
        createTestSession("Filtered Course", "Filtered Location", startTime, endTime);
        String filterDate = startTime.toLocalDate().toString();
        ResponseEntity<SessionDto[]> response = client.getForEntity("/sessions/filter?date=" + filterDate, SessionDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length >= 1);
        LocalDateTime responseStartTime = LocalDateTime.parse(response.getBody()[0].getStartTime().toString()).truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime responseEndTime = LocalDateTime.parse(response.getBody()[0].getEndTime().toString()).truncatedTo(ChronoUnit.SECONDS);
        assertEquals(startTime, responseStartTime);
        assertEquals(endTime, responseEndTime);
    }

    @Test
    public void testViewFilteredSessionsWithInvalidDate() {
        ResponseEntity<String> response = client.getForEntity("/sessions/filter?date=invalid-date", String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testCreateSession() throws Exception {
        Course course = new Course();
        course.setName("Sample Course");
        courseRepository.save(course);
        Location location = new Location();
        location.setName("Sample Location");
        locationRepository.save(location);
        String urlTemplate = UriComponentsBuilder.fromPath("/session")
                .queryParam("startTime", "2024-03-15T13:00:00")
                .queryParam("endTime", "2024-03-15T14:00:00")
                .queryParam("courseName", "Sample Course")
                .queryParam("locationName", "Sample Location")
                .encode()
                .toUriString();
        ResponseEntity<SessionDto> postResponse = client.postForEntity(urlTemplate, null, SessionDto.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
    }

    @Test
    public void testCreateSessionWithInvalidData() {
        String urlTemplate = UriComponentsBuilder.fromPath("/session")
                .queryParam("courseName", "Sample Course")
                .queryParam("locationName", "Sample Location")
                .encode()
                .toUriString();
        ResponseEntity<String> postResponse = client.postForEntity(urlTemplate, null, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, postResponse.getStatusCode());
    }

    private Session createTestSession(String courseName, String locationName, LocalDateTime startTime, LocalDateTime endTime) {
        Session session = new Session();
        session.setStartTime(startTime);
        session.setEndTime(endTime);
        return sessionRepository.save(session);
    }

    @Test
    public void testDeleteSession() {
        LocalDateTime startTime = LocalDateTime.now().plusDays(1);
        LocalDateTime endTime = startTime.plusHours(1);
        Session testSession = createTestSession("Sample Course", "Sample Location", startTime, endTime);
        assertTrue(sessionRepository.findById(testSession.getId()).isPresent());
        client.delete("/sessions/{id}", testSession.getId());
        ResponseEntity<String> response = client.getForEntity("/sessions/filter/{ids}", String.class, testSession.getId());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(sessionRepository.findById(testSession.getId()).isPresent());
    }

    @Test
    public void testDeleteNonExistentSession() {
        int nonExistentSessionId = 999999;
        client.delete("/sessions/{id}", nonExistentSessionId);
        ResponseEntity<String> response = client.getForEntity("/sessions/filter/{ids}", String.class, nonExistentSessionId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
