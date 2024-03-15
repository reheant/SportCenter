package ca.mcgill.ecse321.sportscenter.service;

import ca.mcgill.ecse321.sportscenter.dao.CourseRepository;
import ca.mcgill.ecse321.sportscenter.model.Course;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseIntegrationTests {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private CourseRepository courseRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        courseRepository.deleteAll();
    }

    @Test
    public void testViewFilteredCourses() {
        Course course1 = new Course();
        course1.setName("Test Course 1");
        course1.setDescription("A test course");
        courseRepository.save(course1);
        Course course2 = new Course();
        course2.setName("Another Test Course");
        course2.setDescription("Another test course description");
        courseRepository.save(course2);
        ResponseEntity<Course[]> response = client.getForEntity("/courses?keyword=Test", Course[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length >= 1);
        assertEquals("Test Course 1", response.getBody()[0].getName());
    }

    @Test
    public void testViewFilteredCoursesWithInvalidKeyword() {
        ResponseEntity<Course[]> response = client.getForEntity("/courses?keyword=UnlikelyKeyword123456", Course[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().length);
    }

    @Test
    public void testDeleteCourse() {
        Course course = new Course();
        course.setName("Course to Delete");
        course = courseRepository.save(course);
        client.delete("/courses/{id}", course.getId());
        Optional<Course> deletedCourse = courseRepository.findById(course.getId());
        assertTrue(((Optional<?>) deletedCourse).isEmpty());
    }

    @Test
    public void testDeleteNonExistentCourse() {
        int nonExistentCourseId = 999999;
        client.delete("/courses/{id}", nonExistentCourseId);
        ResponseEntity<String> response = client.getForEntity("/courses/filter/{ids}", String.class, nonExistentCourseId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}