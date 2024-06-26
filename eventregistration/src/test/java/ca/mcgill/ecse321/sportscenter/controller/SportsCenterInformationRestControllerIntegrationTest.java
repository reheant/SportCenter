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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import ca.mcgill.ecse321.sportscenter.dao.*;
import ca.mcgill.ecse321.sportscenter.dto.*;
import ca.mcgill.ecse321.sportscenter.model.*;
import ca.mcgill.ecse321.sportscenter.model.Course.CourseStatus;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SportsCenterInformationRestControllerIntegrationTest {
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
    private AccountRepository accountRepo;

    @BeforeEach
    @AfterEach
    public void clearDb() {
        instructorRepo.deleteAll();
        accountRepo.deleteAll();
        sessionRepo.deleteAll();
        courseRepo.deleteAll();
        locationRepo.deleteAll();
    }

    @Test
    public void testModifySCCourse() {
        Course course = new Course("Zumba", "fun zumba for all", CourseStatus.Approved, true, 1.0f, 20.0f);
        courseRepo.save(course);
        
        String urlTemplate = UriComponentsBuilder.fromPath("/sportscenter/modify/courses")
        .queryParam("courseId", course.getId())
        .encode()
        .toUriString();
        ResponseEntity<CourseDto> response = client.postForEntity(urlTemplate, course, CourseDto.class);

		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
    }
    @Test
    public void testModifySCLocation() {
        Time openingTime = Time.valueOf("7:00:00");
        Time closingTime = Time.valueOf("18:00:00");
        Location location = new Location("Studio A", 30, openingTime, closingTime);        
        locationRepo.save(location);
        
        String urlTemplate = UriComponentsBuilder.fromPath("/sportscenter/modify/locations")
        .queryParam("locationId", location.getId())
        .encode()
        .toUriString();
        ResponseEntity<LocationDto> response = client.postForEntity(urlTemplate, location, LocationDto.class);

		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
    }
    @Test
    public void testModifySCInstructor() {
        Account acc = new Account("Alex", "Whaba", "awhaba@gmail.com", "aBc123!");
        accountRepo.save(acc);
        Instructor instructor = new Instructor(acc);
        instructorRepo.save(instructor);

        String urlTemplate = UriComponentsBuilder.fromPath("/sportscenter/modify/instructors")
                .queryParam("instructorId", instructor.getId())
                .queryParam("newFirstName", "Tiffany")
                .queryParam ("newLastName", "Miller")
                .encode()
                .toUriString();

        Account account = instructor.getAccount();
        InstructorDto instructorDto = new InstructorDto(account.getEmail());

        ResponseEntity<InstructorDto> response = client.postForEntity(urlTemplate, instructorDto, InstructorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
    }


}
