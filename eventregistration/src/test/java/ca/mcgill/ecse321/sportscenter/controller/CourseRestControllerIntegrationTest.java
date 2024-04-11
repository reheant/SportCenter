package ca.mcgill.ecse321.sportscenter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import ca.mcgill.ecse321.sportscenter.dao.CourseRepository;
import ca.mcgill.ecse321.sportscenter.dto.CourseDto;
import ca.mcgill.ecse321.sportscenter.model.Course;
import ca.mcgill.ecse321.sportscenter.model.Course.CourseStatus;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CourseRestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private CourseRepository courseRepo;

    @AfterEach
    public void clearDb() {
        courseRepo.deleteAll();

    }

    private Course createDefaultCourse() {
        return courseRepo.save(new Course("Musculation", "Pushing Weights", CourseStatus.Pending, true, (float) 10.101,
                (float) 294.2));
    }

    @Test
    public void testCreateCourse() {
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();

        String name = "Yoga";
        String description = "This is a description";
        Boolean requiresInstructor = true;
        float defaultDuration = (float) 10.012;
        float cost = (float) 293.203;

        requestBody.add("description", description);
        requestBody.add("requiresInstructor", requiresInstructor);
        requestBody.add("defaultDuration", defaultDuration);
        requestBody.add("cost", cost);

        ResponseEntity<CourseDto> response = client.postForEntity("/course/{name}", requestBody, CourseDto.class, name);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
        assertNotNull(response.getBody(), "Response has body");
        assertEquals(name, response.getBody().getName(), "Response has correct name");
        assertEquals(description, response.getBody().getDescription(), "Response has correct description");
        assertEquals(requiresInstructor, response.getBody().getRequiresInstructor(),
                "Response has correct requires instructor");
        assertEquals(defaultDuration, response.getBody().getDefaultDuration(), "Response has correct duration");
        assertEquals(cost, response.getBody().getCost(), "Response has correct cost");
    }

    @Test
    public void testCreateDuplicateCourse() {
        createDefaultCourse();

        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();

        String name = "Musculation";
        String description = "This is a description";
        Boolean requiresInstructor = true;
        float defaultDuration = (float) 10.012;
        float cost = (float) 293.203;

        requestBody.add("description", description);
        requestBody.add("requiresInstructor", requiresInstructor);
        requestBody.add("defaultDuration", defaultDuration);
        requestBody.add("cost", cost);

        ResponseEntity<String> response = client.postForEntity("/course/{name}", requestBody, String.class, name);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    }

    @Test
    public void testCreateCourseInvalidName() {

        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();

        String name = "";
        String description = "This is a description";
        Boolean requiresInstructor = true;
        float defaultDuration = (float) 10.012;
        float cost = (float) 293.203;

        requestBody.add("description", description);
        requestBody.add("requiresInstructor", requiresInstructor);
        requestBody.add("defaultDuration", defaultDuration);
        requestBody.add("cost", cost);

        ResponseEntity<String> response = client.postForEntity("/course/{name}", requestBody, String.class, name);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Response has correct status");
    }

    @Test
    public void testCreateCourseNullName() {

        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();

        String name = null;
        String description = "This is a description";
        Boolean requiresInstructor = true;
        float defaultDuration = (float) 10.012;
        float cost = (float) 293.203;

        requestBody.add("description", description);
        requestBody.add("requiresInstructor", requiresInstructor);
        requestBody.add("defaultDuration", defaultDuration);
        requestBody.add("cost", cost);

        ResponseEntity<String> response = client.postForEntity("/course/{name}", requestBody, String.class, name);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Response has correct status");
    }

    @Test
    public void testCreateCourseNullDescription() {

        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();

        String name = "Sport";
        String description = null;
        Boolean requiresInstructor = true;
        float defaultDuration = (float) 10.012;
        float cost = (float) 293.203;

        requestBody.add("description", description);
        requestBody.add("requiresInstructor", requiresInstructor);
        requestBody.add("defaultDuration", defaultDuration);
        requestBody.add("cost", cost);

        ResponseEntity<String> response = client.postForEntity("/course/{name}", requestBody, String.class, name);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    }

    @Test
    public void testCreateCourseInvalidDescription() {

        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();

        String name = "Sport";
        String description = "";
        Boolean requiresInstructor = true;
        float defaultDuration = (float) 10.012;
        float cost = (float) 293.203;

        requestBody.add("description", description);
        requestBody.add("requiresInstructor", requiresInstructor);
        requestBody.add("defaultDuration", defaultDuration);
        requestBody.add("cost", cost);

        ResponseEntity<String> response = client.postForEntity("/course/{name}", requestBody, String.class, name);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    }

    @Test
    public void testCreateCourseInvalidDuration() {

        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();

        String name = "Sport";
        String description = "This is a sport";
        Boolean requiresInstructor = true;
        float defaultDuration = (float) -10.012;
        float cost = (float) 293.203;

        requestBody.add("description", description);
        requestBody.add("requiresInstructor", requiresInstructor);
        requestBody.add("defaultDuration", defaultDuration);
        requestBody.add("cost", cost);

        ResponseEntity<String> response = client.postForEntity("/course/{name}", requestBody, String.class, name);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    }

    @Test
    public void testCreateCourseInvalidCost() {

        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();

        String name = "Test";
        String description = "This is a description";
        Boolean requiresInstructor = true;
        float defaultDuration = (float) 10.012;
        float cost = (float) -293.203;

        requestBody.add("description", description);
        requestBody.add("requiresInstructor", requiresInstructor);
        requestBody.add("defaultDuration", defaultDuration);
        requestBody.add("cost", cost);

        ResponseEntity<String> response = client.postForEntity("/course/{name}", requestBody, String.class, name);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    }

    @Test
    public void testApproveCourse() {
        Course course = createDefaultCourse();

        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();

        String name = "Musculation";

        requestBody.add("name", name);

        UriComponents uriComponents = UriComponentsBuilder.fromUriString("/approve/{name}").buildAndExpand(name);

        ResponseEntity<CourseDto> response = client.postForEntity(uriComponents.toUriString(), requestBody,
                CourseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
        assertNotNull(response.getBody(), "Response has body");
        assertEquals(name, response.getBody().getName(), "Response has correct name");
        assertEquals(course.getDescription(), response.getBody().getDescription(), "Response has correct description");
        assertEquals(course.getRequiresInstructor(), response.getBody().getRequiresInstructor(),
                "Response has correct requires instructor");
        assertEquals(course.getDefaultDuration(), response.getBody().getDefaultDuration(),
                "Response has correct duration");
        assertEquals(course.getCost(), response.getBody().getCost(), "Response has correct cost");
        assertEquals(CourseStatus.Approved, response.getBody().getCourseStatus(), "Response has correct course status");

    }

    @Test
    public void testApproveInexistantCourse() {

        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();

        String name = "Musculation";

        requestBody.add("name", name);

        ResponseEntity<String> response = client.postForEntity("/approve/{name}", requestBody, String.class, name);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    }

    @Test
    public void testDisapproveCourse() {
        Course course = createDefaultCourse();

        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();

        String name = "Musculation";

        requestBody.add("name", name);

        UriComponents uriComponents = UriComponentsBuilder.fromUriString("/disapprove/{name}").buildAndExpand(name);

        ResponseEntity<CourseDto> response = client.postForEntity(uriComponents.toUriString(), requestBody,
                CourseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
        assertNotNull(response.getBody(), "Response has body");
        assertEquals(name, response.getBody().getName(), "Response has correct name");
        assertEquals(course.getDescription(), response.getBody().getDescription(), "Response has correct description");
        assertEquals(course.getRequiresInstructor(), response.getBody().getRequiresInstructor(),
                "Response has correct requires instructor");
        assertEquals(course.getDefaultDuration(), response.getBody().getDefaultDuration(),
                "Response has correct duration");
        assertEquals(course.getCost(), response.getBody().getCost(), "Response has correct cost");
        assertEquals(CourseStatus.Refused, response.getBody().getCourseStatus(), "Response has correct course status");

    }

    @Test
    public void testDisapproveInexistantCourse() {

        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();

        String name = "Musculation";

        requestBody.add("name", name);

        ResponseEntity<String> response = client.postForEntity("/disapprove/{name}", requestBody, String.class, name);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    }

    @Test
    public void testViewFilteredCourses() {
        Course course1 = new Course();
        course1.setName("Test Course 1");
        course1.setDescription("A test course");
        courseRepo.save(course1);
        Course course2 = new Course();
        course2.setName("Another Test Course");
        course2.setDescription("Another test course description");
        courseRepo.save(course2);
        ResponseEntity<Course[]> response = client.getForEntity("/courses?keyword=Test", Course[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length >= 1);
        assertEquals("Test Course 1", response.getBody()[0].getName());
    }

    @Test
    public void testViewFilteredCoursesWithInvalidKeyword() {
        ResponseEntity<Course[]> response = client.getForEntity("/courses?keyword=UnlikelyKeyword123456",
                Course[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().length);
    }

    @Test
    public void testDeleteCourse() {
        Course course = new Course();
        course.setName("Course to Delete");
        course = courseRepo.save(course);
        client.delete("/courses/{id}", course.getId());
        Optional<Course> deletedCourse = courseRepo.findById(course.getId());
        assertTrue(((Optional<?>) deletedCourse).isEmpty());
    }

    @Test
    public void testDeleteNonExistentCourse() {
        int nonExistentCourseId = 999999;
        client.delete("/courses/{id}", nonExistentCourseId);
        ResponseEntity<String> response = client.getForEntity("/courses/filter/{ids}", String.class,
                nonExistentCourseId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
