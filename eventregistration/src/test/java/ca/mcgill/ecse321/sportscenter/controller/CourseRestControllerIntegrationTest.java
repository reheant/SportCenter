package ca.mcgill.ecse321.sportscenter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportscenter.dao.CourseRepository;
import ca.mcgill.ecse321.sportscenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportscenter.dto.CourseDto;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Course;
import ca.mcgill.ecse321.sportscenter.model.Course.CourseStatus;
import ca.mcgill.ecse321.sportscenter.model.Owner;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CourseRestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private OwnerRepository ownerRepo;

    
    @AfterEach
    public void clearDb() {
        courseRepo.deleteAll();
        ownerRepo.deleteAll();
        accountRepo.deleteAll();
        
    }

    private Course createDefaultCourse(){
        return courseRepo.save(new Course("Musculation", "Pushing Weights",CourseStatus.Pending, true,(float) 10.101,(float) 294.2));
    }
    private Account createDefaultPerson() {
        return accountRepo.save(new Account("foo", "bar", "foo@bar.com", "password123A!"));
    }
    
    private Owner createDefaultOwner() {
        return ownerRepo.save(new Owner(createDefaultPerson()));
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
        assertEquals(requiresInstructor, response.getBody().getRequiresInstructor(), "Response has correct requires instructor");
        assertEquals(defaultDuration, response.getBody().getDefaultDuration(), "Response has correct duration");
        assertEquals(cost, response.getBody().getCost(), "Response has correct cost");
    }
    

    @Test
    public void testCreateDuplicateCourse(){

       Course course = createDefaultCourse();

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

        ResponseEntity<CourseDto> response = client.postForEntity("/course/{name}", requestBody, CourseDto.class, name);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    }

    public void testCreateCourseInvalidName(){
 
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
 
         ResponseEntity<CourseDto> response = client.postForEntity("/course/{name}", requestBody, CourseDto.class, name);
 
         assertNotNull(response);
         assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
     }



     public void testCreateCourseNullName(){
 
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
 
         ResponseEntity<CourseDto> response = client.postForEntity("/course/{name}", requestBody, CourseDto.class, name);
 
         assertNotNull(response);
         assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
     }

     public void testCreateCourseNullDescription(){
 
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
 
         ResponseEntity<CourseDto> response = client.postForEntity("/course/{name}", requestBody, CourseDto.class, name);
 
         assertNotNull(response);
         assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
     }



     public void testCreateCourseInvalidDescription(){
 
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
 
         ResponseEntity<CourseDto> response = client.postForEntity("/course/{name}", requestBody, CourseDto.class, name);
 
         assertNotNull(response);
         assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
     }

     public void testCreateCourseInvalidDuration(){
 
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
 
         ResponseEntity<CourseDto> response = client.postForEntity("/course/{name}", requestBody, CourseDto.class, name);
 
         assertNotNull(response);
         assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
     }
     public void testCreateCourseInvalidCost(){
 
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
 
         ResponseEntity<CourseDto> response = client.postForEntity("/course/{name}", requestBody, CourseDto.class, name);
 
         assertNotNull(response);
         assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    }

    @Test
    public void testApproveCourse(){
        Owner owner = createDefaultOwner();
        Course course = createDefaultCourse();

        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();

        String name = "Musculation";
        String email = "foo@bar.com";

        requestBody.add("name", name);
        requestBody.add("email", email);

        ResponseEntity<CourseDto> response = client.postForEntity("/approve/{name}",requestBody, CourseDto.class, name);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
        assertNotNull(response.getBody(), "Response has body");
        assertEquals(name, response.getBody().getName(), "Response has correct name");
        assertEquals(course.getDescription(), response.getBody().getDescription(), "Response has correct description");
        assertEquals(course.getRequiresInstructor(), response.getBody().getRequiresInstructor(), "Response has correct requires instructor");
        assertEquals(course.getDefaultDuration(), response.getBody().getDefaultDuration(), "Response has correct duration");
        assertEquals(course.getCost(), response.getBody().getCost(), "Response has correct cost");
        assertEquals(CourseStatus.Approved, response.getBody().getCourseStatus(), "Response has correct course status");
       
    }


    @Test
    public void testApproveInexistantCourse(){
        Owner owner = createDefaultOwner();

        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();

        String name = "Musculation";
        String email = "foo@bar.com";

        requestBody.add("name", name);
        requestBody.add("email", email);

        ResponseEntity<CourseDto> response = client.postForEntity("/approve/{name}",requestBody, CourseDto.class, name);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    }

    @Test
    public void testApproveInexistantOwner(){
        
        Course course = createDefaultCourse();

        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();

        String name = "Musculation";
        String email = "foo@bar.com";

        requestBody.add("name", name);
        requestBody.add("email", email);

        ResponseEntity<CourseDto> response = client.postForEntity("/approve/{name}",requestBody, CourseDto.class, name);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    }

    @Test
    public void testDisapproveCourse(){
        Owner owner = createDefaultOwner();
        Course course = createDefaultCourse();

        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();

        String name = "Musculation";
        String email = "foo@bar.com";

        requestBody.add("name", name);
        requestBody.add("email", email);

        ResponseEntity<CourseDto> response = client.postForEntity("/disapprove/{name}",requestBody, CourseDto.class, name);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
        assertNotNull(response.getBody(), "Response has body");
        assertEquals(name, response.getBody().getName(), "Response has correct name");
        assertEquals(course.getDescription(), response.getBody().getDescription(), "Response has correct description");
        assertEquals(course.getRequiresInstructor(), response.getBody().getRequiresInstructor(), "Response has correct requires instructor");
        assertEquals(course.getDefaultDuration(), response.getBody().getDefaultDuration(), "Response has correct duration");
        assertEquals(course.getCost(), response.getBody().getCost(), "Response has correct cost");
        assertEquals(CourseStatus.Refused, response.getBody().getCourseStatus(), "Response has correct course status");
       
    }

    @Test
    public void testDisapproveInexistantCourse(){
        Owner owner = createDefaultOwner();

        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();

        String name = "Musculation";
        String email = "foo@bar.com";

        requestBody.add("name", name);
        requestBody.add("email", email);

        ResponseEntity<CourseDto> response = client.postForEntity("/disapprove/{name}",requestBody, CourseDto.class, name);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    }

    @Test
    public void testDisapproveInexistantOwner(){
        
        Course course = createDefaultCourse();

        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();

        String name = "Musculation";
        String email = "foo@bar.com";

        requestBody.add("name", name);
        requestBody.add("email", email);

        ResponseEntity<CourseDto> response = client.postForEntity("/disapprove/{name}",requestBody, CourseDto.class, name);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    }
}

