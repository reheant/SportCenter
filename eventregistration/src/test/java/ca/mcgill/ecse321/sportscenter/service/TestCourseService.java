package ca.mcgill.ecse321.sportscenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportscenter.dao.CourseRepository;
import ca.mcgill.ecse321.sportscenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Course;
import ca.mcgill.ecse321.sportscenter.model.Owner;
import ca.mcgill.ecse321.sportscenter.model.Course.CourseStatus;

@ExtendWith(MockitoExtension.class)
public class TestCourseService {

    @Mock
    private OwnerRepository ownerRepository;
    @Mock 
    private AccountRepository accountRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private CourseService ownerService;

    @InjectMocks
    private CourseService courseService; 


    // For Course
    private static final String name = "Yoga";
    private static final String description = "This is a Yoga. Can't wait to see you there!";
    private static final boolean requiresInstructor = false;
    private static final float duration = (float) 102.41;
    private static final float cost = (float) 12.52;
    private static final CourseStatus courseStatus = CourseStatus.Pending;

    // For Owner
    private static final String firstName = "Rehean";
    private static final String lastName = "Thillai";
    private static final String email = "rehean.thillai@gmail.com";
    private static final String password = "Test1234!";

    private final Owner owner = new Owner();
    private final Account ownerAccount = new Account();
    private final Course course = new Course();


    @BeforeEach
    public void setMockOutput() {
        lenient().when(accountRepository.findAccountByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(email)) {
                ownerAccount.setEmail(email);
                ownerAccount.setFirstName(firstName);
                ownerAccount.setLastName(lastName);
                ownerAccount.setPassword(password);
                owner.setAccount(ownerAccount);
            
                return ownerAccount;
            } else {
                return null;
            }
        });

        lenient().when(ownerRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            ownerAccount.setEmail(email);
            ownerAccount.setFirstName(firstName);
            ownerAccount.setLastName(lastName);
            ownerAccount.setPassword(password);
            owner.setAccount(ownerAccount);
            List<Owner> ownerList = new ArrayList<>();
            ownerList.add(owner);
            return ownerList;

        });
        lenient().when(courseRepository.findCourseByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(name)) {
                Course course = new Course();
                course.setName(name); 
                course.setDescription(description);
                course.setRequiresInstructor(requiresInstructor);
                course.setDefaultDuration(duration);
                course.setCost(cost);
                course.setIsApproved(courseStatus);

                return course;
            } else {
                return null;
            }
        });

        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(courseRepository.save(any(Course.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(ownerRepository.save(any(Owner.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(accountRepository.save(any(Account.class))).thenAnswer(returnParameterAsAnswer);
    }

    @Test 
    public void testCreateCourse(){
        String name = "Yoga Class";
        String description = "This is a Yoga. Can't wait to see you there!";
        boolean requiresInstructor = false;
        float duration = (float) 102.41;
        float cost = (float) 12.52;
        Course course = null;
        
        try {
            course = courseService.createCourse(name, description, requiresInstructor, duration, cost);
        } catch (Exception error) {
            fail(error.getMessage());
        }

        assertNotNull(course);
        assertEquals(name, course.getName());
        assertEquals(description, course.getDescription());
        assertEquals(requiresInstructor, course.getRequiresInstructor());
        assertEquals(duration, course.getDefaultDuration());
        assertEquals(cost, course.getCost());
    }

    @Test 
    public void testCreateCourseNegativeDuration(){
        String name = "Yoga Class";
        String description = "This is a Yoga. Can't wait to see you there!";
        boolean requiresInstructor = false;
        float duration = (float) -102.41;
        float cost = (float) 12.52;
        Course course = null;
        
        try {
            course = courseService.createCourse(name, description, requiresInstructor, duration, cost);
        } catch (Exception error) {
            assertEquals("The duration cannot be zero or negative",error.getMessage());
        }
        assertNull(course);
    }

    @Test 
    public void testCreateCourseZeroDuration(){
        String name = "Yoga Class";
        String description = "This is a Yoga. Can't wait to see you there!";
        boolean requiresInstructor = false;
        float duration = (float) 0;
        float cost = (float) 12.52;
        Course course = null;
        
        try {
            course = courseService.createCourse(name, description, requiresInstructor, duration, cost);
        } catch (Exception error) {
            assertEquals("The duration cannot be zero or negative",error.getMessage());
        }
        assertNull(course);
    }   

    @Test 
    public void testCreateCourseZeroCost(){
        String name = "Yoga Class";
        String description = "This is a Yoga. Can't wait to see you there!";
        boolean requiresInstructor = false;
        float duration = (float) 13.234;
        float cost = (float) 0;
        Course course = null;
        
        try {
            course = courseService.createCourse(name, description, requiresInstructor, duration, cost);
        } catch (Exception error) {
            assertEquals("The price cannot be zero or negative",error.getMessage());
        }
        
        assertNull(course);
    }   


    @Test 
    public void testCreateCourseNegativeCost(){
        String name = "Yoga Class";
        String description = "This is a Yoga. Can't wait to see you there!";
        boolean requiresInstructor = false;
        float duration = (float) 102.41;
        float cost = (float) -12.52;
        Course course = null;
        
        try {
            course = courseService.createCourse(name, description, requiresInstructor, duration, cost);
        } catch (Exception error) {
            assertEquals("The price cannot be zero or negative",error.getMessage());
        }
        
        assertNull(course);
    }

    @Test
    public void testCreateCourseWithDuplicateName() {
        
        String name = "Yoga";
        String description = "This is a Yoga. Can't wait to see you there!";
        boolean requiresInstructor = false;
        float duration = (float) 102.41;
        float cost = (float) 12.52;
        Course course = null;
        
        try {
            course = courseService.createCourse(name, description, requiresInstructor, duration, cost);
        } catch (Exception error) {
            assertEquals("A Course with name " + name + " already exists", error.getMessage());
        }
        assertNull(course);
    }

    @Test 
    public void testCreateCourseNullName(){
        String name = null;
        String description = "This is a Yoga. Can't wait to see you there!";
        boolean requiresInstructor = false;
        float duration = (float) 102.41;
        float cost = (float) 12.52;
        Course course = null;
        
        try {
            course = courseService.createCourse(name, description, requiresInstructor, duration, cost);
        } catch (Exception error) {
            assertEquals("The course requires a name", error.getMessage());
        }
        
        assertNull(course);
    }

    @Test 
    public void testCreateCourseEmptyName(){
        String name = "";
        String description = "This is a Yoga. Can't wait to see you there!";
        boolean requiresInstructor = false;
        float duration = (float) 102.41;
        float cost = (float) 12.52;
        Course course = null;
        
        try {
            course = courseService.createCourse(name, description, requiresInstructor, duration, cost);
        } catch (Exception error) {
            assertEquals("The course requires a name",error.getMessage());
        }
        
        assertNull(course);
    }

    @Test 
    public void testCreateCourseEmptyDescription(){
        String name = "Yoga Class";
        String description = "";
        boolean requiresInstructor = false;
        float duration = (float) 102.41;
        float cost = (float) 12.52;
        Course course = null;
        
        try {
            course = courseService.createCourse(name, description, requiresInstructor, duration, cost);
        } catch (Exception error) {
            assertEquals("The course requires a description",error.getMessage());
        }
        
        assertNull(course);
    }

    @Test 
    public void testCreateCourseNullDescription(){
        String name = "Yoga Class";
        String description = null;
        boolean requiresInstructor = false;
        float duration = (float) 102.41;
        float cost = (float) 12.52;
        Course course = null;
        
        try {
            course = courseService.createCourse(name, description, requiresInstructor, duration, cost);
        } catch (Exception error) {
            assertEquals("The course requires a description",error.getMessage());
        }
        
        assertNull(course);
    }


    @Test 
    public void testApproveCourse(){
        
        Course outputCourse = null;
        Course course = new Course();
        lenient().when(courseRepository.findCourseByName(anyString())).thenReturn(course);
        try {
            outputCourse = courseService.approveCourse(name, email);

        } catch (Exception error){
            fail(error.getMessage());
        }

        assertTrue(outputCourse.getIsApproved().equals(CourseStatus.Approved));
        assertEquals(outputCourse, courseRepository.findCourseByName(name));
        assertEquals(CourseStatus.Approved,courseRepository.findCourseByName(name).getIsApproved()); 
 
        
        
    }

    @Test 
    public void testApproveCourseOwnerNull(){
        
        Course course = new Course();

        course.setName(name); 
        course.setDescription(description);
        course.setRequiresInstructor(requiresInstructor);
        course.setDefaultDuration(duration);
        course.setCost(cost);
        course.setIsApproved(courseStatus);
        

        try {
            courseService.approveCourse(name, null);
        } catch (Exception error) {
            assertEquals("email is null", error.getMessage());

        }
        assertEquals(CourseStatus.Pending,course.getIsApproved());
    }

    @Test 
    public void testApproveCourseOwnerNotFound(){

        Course course = new Course();

        course.setName(name); 
        course.setDescription(description);
        course.setRequiresInstructor(requiresInstructor);
        course.setDefaultDuration(duration);
        course.setCost(cost);
        course.setIsApproved(courseStatus);

        String notFoundOwner = "marc@mail.com";

        try {
            courseService.approveCourse(name, notFoundOwner);
        } catch (Exception error) {
            assertEquals("Email is not accociated to an account", error.getMessage());

        }
        assertEquals(CourseStatus.Pending,course.getIsApproved());
    }

    @Test 
    public void testApproveCourseCourseNull(){
        course.setIsApproved(courseStatus);
        try {
            courseService.approveCourse(null, email);
        } catch (Exception error ){
            assertEquals("Requires a name", error.getMessage());
        }
        assertEquals(CourseStatus.Pending,course.getIsApproved());
    }

    @Test 
    public void testApproveCourseCourseNotFound(){

        
        String courseName = "unfound";
        lenient().when(courseRepository.findCourseByName(anyString())).thenReturn(null);

        try {
            courseService.approveCourse(courseName, email);
        } catch (Exception error ){

            assertEquals("A Course with name " + courseName + " does not exists", error.getMessage());
        }
        assertNull(courseRepository.findCourseByName("unfound"));
    }

    @Test 
    public void testDisapproveCourse(){
        Course outputCourse = null;
        Course course = new Course();
        lenient().when(courseRepository.findCourseByName(anyString())).thenReturn(course);
        try {
            outputCourse = courseService.disapproveCourse(name, email);
            
        } catch (Exception error){
            fail(error.getMessage());
        }

        assertTrue(outputCourse.getIsApproved().equals(CourseStatus.Refused));
        assertEquals(outputCourse, courseRepository.findCourseByName(name));
        assertEquals(CourseStatus.Refused,courseRepository.findCourseByName(name).getIsApproved()); 

    }

    @Test 
    public void testDisapproveCourseOwnerNull(){

        Course course = new Course();

        course.setName(name); 
        course.setDescription(description);
        course.setRequiresInstructor(requiresInstructor);
        course.setDefaultDuration(duration);
        course.setCost(cost);
        course.setIsApproved(courseStatus);

        try {
            courseService.disapproveCourse(name, null);
        } catch (Exception error) {
            assertEquals("email is null", error.getMessage());

        }
    }

    @Test 
    public void testDisapproveCourseOwnerNotFound(){
        course.setIsApproved(courseStatus);
        try {
            courseService.disapproveCourse(null, email);
        } catch (Exception error ){
            assertEquals("Requires a name", error.getMessage());
        }
        assertEquals(CourseStatus.Pending, course.getIsApproved());
    }

    @Test 
    public void testDisapproveCourseCourseNull(){
        course.setIsApproved(courseStatus);
        try {
            courseService.disapproveCourse(null, email);
        } catch (Exception error ){
            assertEquals("Requires a name", error.getMessage());
        }
        assertEquals(CourseStatus.Pending,course.getIsApproved());

    }

    @Test 
    public void testDisapproveCourseCourseNotFound(){

        course.setIsApproved(courseStatus);
        String courseName = "unfound";

        try {
            courseService.disapproveCourse(courseName, email);
        } catch (Exception error ){

            assertEquals("A Course with name " + courseName + " does not exists", error.getMessage());
        }

        assertEquals(CourseStatus.Pending,course.getIsApproved());
    }

}
