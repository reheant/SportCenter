package ca.mcgill.ecse321.sportscenter.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
                course.setCourseStatus(courseStatus);

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
        lenient().when(courseRepository.findCoursesByIdIn(any(Collection.class))).thenAnswer((InvocationOnMock invocation) -> {
            Collection<Integer> ids = invocation.getArgument(0);
            List<Course> courses = new ArrayList<>();
            if(ids.contains(1)) {
                Course course = new Course();
                courses.add(course);
            }
            return courses;
        });
        lenient().when(courseRepository.findByKeywordInNameOrDescription(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            String keywordArg = invocation.getArgument(0);
            if ("SpecificName".equalsIgnoreCase(keywordArg) || "SpecificDescription".equalsIgnoreCase(keywordArg)) {
                Course specificCourse = new Course();
                specificCourse.setName("SomeName");
                specificCourse.setDescription("SomeDescription");
                return Arrays.asList(specificCourse);
            } else {
                return new ArrayList<>();
            }
        });
        lenient().when(courseRepository.findCoursesByCourseStatus(any(CourseStatus.class))).thenAnswer((InvocationOnMock invocation) -> {
            CourseStatus statusArg = invocation.getArgument(0);
            List<Course> courses = new ArrayList<>();
            if (statusArg == CourseStatus.Approved) { // Adjust according to your CourseStatus enum or class
                Course activeCourse = new Course();
                activeCourse.setCourseStatus(CourseStatus.Approved);
                courses.add(activeCourse);
            }
            return courses;
        });
        lenient().when(courseRepository.findCoursesByRequiresInstructor(any(Boolean.class))).thenAnswer((InvocationOnMock invocation) -> {
            boolean requiresInstructor = invocation.getArgument(0);
            List<Course> courses = new ArrayList<>();
            if (requiresInstructor) {
                Course course = new Course();
                course.setName("Instructor Requiring Course");
                course.setRequiresInstructor(true);
                courses.add(course);
            }
            return courses;
        });
        lenient().when(courseRepository.findCoursesByDefaultDuration(any(Float.class))).thenAnswer((InvocationOnMock invocation) -> {
            float defaultDuration = invocation.getArgument(0);
            List<Course> courses = new ArrayList<>();
            Course course = new Course();
            course.setName("Duration Specific Course");
            course.setDefaultDuration(defaultDuration);
            courses.add(course);
            return courses;
        });
        lenient().when(courseRepository.findCoursesByCost(any(Float.class))).thenAnswer((InvocationOnMock invocation) -> {
            float cost = invocation.getArgument(0);
            List<Course> courses = new ArrayList<>();
            Course course = new Course();
            course.setName("Cost Specific Course");
            course.setCost(cost);
            courses.add(course);
            return courses;
        });
    }

    @Test 
    public void testCreateCourse(){
        String name = "Yoga Class";
        String description = "This is a Yoga. Can't wait to see you there!";
        CourseStatus courseStatus = CourseStatus.Pending;
        boolean requiresInstructor = false;
        float duration = (float) 102.41;
        float cost = (float) 12.52;
        Course course = null;
        
        try {
            course = courseService.createCourse(name, description, courseStatus, requiresInstructor, duration, cost);
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
        CourseStatus courseStatus = CourseStatus.Pending;
        boolean requiresInstructor = false;
        float duration = (float) -102.41;
        float cost = (float) 12.52;
        Course course = null;
        
        try {
            course = courseService.createCourse(name, description, courseStatus, requiresInstructor, duration, cost);
        } catch (Exception error) {
            assertEquals("The duration cannot be zero or negative",error.getMessage());
        }
        assertNull(course);
    }

    @Test 
    public void testCreateCourseZeroDuration(){
        String name = "Yoga Class";
        String description = "This is a Yoga. Can't wait to see you there!";
        CourseStatus courseStatus = CourseStatus.Pending;
        boolean requiresInstructor = false;
        float duration = (float) 0;
        float cost = (float) 12.52;
        Course course = null;
        
        try {
            course = courseService.createCourse(name, description, courseStatus, requiresInstructor, duration, cost);
        } catch (Exception error) {
            assertEquals("The duration cannot be zero or negative",error.getMessage());
        }
        assertNull(course);
    }   

    @Test 
    public void testCreateCourseZeroCost(){
        String name = "Yoga Class";
        String description = "This is a Yoga. Can't wait to see you there!";
        CourseStatus courseStatus = CourseStatus.Pending;
        boolean requiresInstructor = false;
        float duration = (float) 13.234;
        float cost = (float) 0;
        Course course = null;
        
        try {
            course = courseService.createCourse(name, description, courseStatus, requiresInstructor, duration, cost);
        } catch (Exception error) {
            assertEquals("The price cannot be zero or negative",error.getMessage());
        }
        
        assertNull(course);
    }   


    @Test 
    public void testCreateCourseNegativeCost(){
        String name = "Yoga Class";
        String description = "This is a Yoga. Can't wait to see you there!";
        CourseStatus courseStatus = CourseStatus.Pending;
        boolean requiresInstructor = false;
        float duration = (float) 102.41;
        float cost = (float) -12.52;
        Course course = null;
        
        try {
            course = courseService.createCourse(name, description, courseStatus, requiresInstructor, duration, cost);
        } catch (Exception error) {
            assertEquals("The price cannot be zero or negative",error.getMessage());
        }
        
        assertNull(course);
    }

    @Test
    public void testCreateCourseWithDuplicateName() {
        
        String name = "Yoga";
        String description = "This is a Yoga. Can't wait to see you there!";
        CourseStatus courseStatus = CourseStatus.Pending;
        boolean requiresInstructor = false;
        float duration = (float) 102.41;
        float cost = (float) 12.52;
        Course course = null;
        
        try {
            course = courseService.createCourse(name, description, courseStatus, requiresInstructor, duration, cost);
        } catch (Exception error) {
            assertEquals("A Course with name " + name + " already exists", error.getMessage());
        }
        assertNull(course);
    }

    @Test 
    public void testCreateCourseNullName(){
        String name = null;
        String description = "This is a Yoga. Can't wait to see you there!";
        CourseStatus courseStatus = CourseStatus.Pending;
        boolean requiresInstructor = false;
        float duration = (float) 102.41;
        float cost = (float) 12.52;
        Course course = null;
        
        try {
            course = courseService.createCourse(name, description, courseStatus, requiresInstructor, duration, cost);
        } catch (Exception error) {
            assertEquals("The course requires a name", error.getMessage());
        }
        
        assertNull(course);
    }

    @Test 
    public void testCreateCourseEmptyName(){
        String name = "";
        String description = "This is a Yoga. Can't wait to see you there!";
        CourseStatus courseStatus = CourseStatus.Pending;
        boolean requiresInstructor = false;
        float duration = (float) 102.41;
        float cost = (float) 12.52;
        Course course = null;
        
        try {
            course = courseService.createCourse(name, description, courseStatus, requiresInstructor, duration, cost);
        } catch (Exception error) {
            assertEquals("The course requires a name",error.getMessage());
        }
        
        assertNull(course);
    }

    @Test 
    public void testCreateCourseEmptyDescription(){
        String name = "Yoga Class";
        String description = "";
        CourseStatus courseStatus = CourseStatus.Pending;
        boolean requiresInstructor = false;
        float duration = (float) 102.41;
        float cost = (float) 12.52;
        Course course = null;
        
        try {
            course = courseService.createCourse(name, description, courseStatus, requiresInstructor, duration, cost);
        } catch (Exception error) {
            assertEquals("The course requires a description",error.getMessage());
        }
        
        assertNull(course);
    }

    @Test 
    public void testCreateCourseNullDescription(){
        String name = "Yoga Class";
        String description = null;
        CourseStatus courseStatus = CourseStatus.Pending;
        boolean requiresInstructor = false;
        float duration = (float) 102.41;
        float cost = (float) 12.52;
        Course course = null;
        
        try {
            course = courseService.createCourse(name, description, courseStatus, requiresInstructor, duration, cost);
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

        assertTrue(outputCourse.getCourseStatus().equals(CourseStatus.Approved));
        assertEquals(outputCourse, courseRepository.findCourseByName(name));
        assertEquals(CourseStatus.Approved,courseRepository.findCourseByName(name).getCourseStatus()); 
 
        
        
    }

    @Test 
    public void testApproveCourseOwnerNull(){
        
        Course course = new Course();

        course.setName(name); 
        course.setDescription(description);
        course.setRequiresInstructor(requiresInstructor);
        course.setDefaultDuration(duration);
        course.setCost(cost);
        course.setCourseStatus(courseStatus);
        

        try {
            courseService.approveCourse(name, null);
        } catch (Exception error) {
            assertEquals("email is null", error.getMessage());

        }
        assertEquals(CourseStatus.Pending,course.getCourseStatus());
    }

    @Test 
    public void testApproveCourseOwnerNotFound(){

        Course course = new Course();

        course.setName(name); 
        course.setDescription(description);
        course.setRequiresInstructor(requiresInstructor);
        course.setDefaultDuration(duration);
        course.setCost(cost);
        course.setCourseStatus(courseStatus);

        String notFoundOwner = "marc@mail.com";

        try {
            courseService.approveCourse(name, notFoundOwner);
        } catch (Exception error) {
            assertEquals("Email is not accociated to an account", error.getMessage());

        }
        assertEquals(CourseStatus.Pending,course.getCourseStatus());
    }

    @Test 
    public void testApproveCourseCourseNull(){
        course.setCourseStatus(courseStatus);
        try {
            courseService.approveCourse(null, email);
        } catch (Exception error ){
            assertEquals("Requires a name", error.getMessage());
        }
        assertEquals(CourseStatus.Pending,course.getCourseStatus());
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

        assertTrue(outputCourse.getCourseStatus().equals(CourseStatus.Refused));
        assertEquals(outputCourse, courseRepository.findCourseByName(name));
        assertEquals(CourseStatus.Refused,courseRepository.findCourseByName(name).getCourseStatus()); 

    }

    @Test 
    public void testDisapproveCourseOwnerNull(){

        Course course = new Course();

        course.setName(name); 
        course.setDescription(description);
        course.setRequiresInstructor(requiresInstructor);
        course.setDefaultDuration(duration);
        course.setCost(cost);
        course.setCourseStatus(courseStatus);

        try {
            courseService.disapproveCourse(name, null);
        } catch (Exception error) {
            assertEquals("email is null", error.getMessage());

        }
    }

    @Test 
    public void testDisapproveCourseOwnerNotFound(){
        course.setCourseStatus(courseStatus);
        try {
            courseService.disapproveCourse(null, email);
        } catch (Exception error ){
            assertEquals("Requires a name", error.getMessage());
        }
        assertEquals(CourseStatus.Pending, course.getCourseStatus());
    }

    @Test 
    public void testDisapproveCourseCourseNull(){
        course.setCourseStatus(courseStatus);
        try {
            courseService.disapproveCourse(null, email);
        } catch (Exception error ){
            assertEquals("Requires a name", error.getMessage());
        }
        assertEquals(CourseStatus.Pending,course.getCourseStatus());

    }

    @Test 
    public void testDisapproveCourseCourseNotFound(){

        course.setCourseStatus(courseStatus);
        String courseName = "unfound";

        try {
            courseService.disapproveCourse(courseName, email);
        } catch (Exception error ){

            assertEquals("A Course with name " + courseName + " does not exists", error.getMessage());
        }

        assertEquals(CourseStatus.Pending,course.getCourseStatus());
    }

    @Test
    public void testViewAllCourses() throws Exception {
        Course course = new Course();
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course));

        List<Course> courses = courseService.getAllCourses();

        assertNotNull(courses);
        assertEquals(1, courses.size());
        verify(courseRepository).findAll();
    }

    @Test
    public void testViewFilteredCoursesByIds() throws Exception {
        List<Integer> ids = Arrays.asList(1);
        List<Course> filteredCourses = courseService.viewFilteredCourses(ids, null, null, null, null, null);
        assertNotNull(filteredCourses);
        assertEquals(1, filteredCourses.size());
        verify(courseRepository).findCoursesByIdIn(ids);
    }

    @Test
    public void testViewFilteredCoursesByKeyword() throws Exception {
        List<Course> filteredCourses = courseService.viewFilteredCourses(null, "SpecificName", null, null, null, null);
        assertNotNull(filteredCourses);
        assertEquals(1, filteredCourses.size());
        verify(courseRepository).findByKeywordInNameOrDescription("SpecificName");
    }

    @Test
    public void testViewFilteredCoursesByStatus() throws Exception {
        CourseStatus statusToFilter = CourseStatus.Approved;
        List<Course> filteredCourses = courseService.viewFilteredCourses(null, null, statusToFilter, null, null, null);
        assertNotNull(filteredCourses);
        assertFalse(filteredCourses.isEmpty());
        assertEquals(CourseStatus.Approved, filteredCourses.get(0).getCourseStatus());
        verify(courseRepository).findCoursesByCourseStatus(statusToFilter);
    }

    @Test
    public void testViewFilteredCoursesByRequiresInstructor() throws Exception {
        List<Course> filteredCourses = courseService.viewFilteredCourses(null, null, null, true, null, null);

        assertNotNull(filteredCourses);
        assertEquals(1, filteredCourses.size());
        verify(courseRepository).findCoursesByRequiresInstructor(true);
    }

    @Test
    public void testViewFilteredCoursesByDefaultDuration() throws Exception {
        float testDuration = 10.0f;
        List<Course> filteredCourses = courseService.viewFilteredCourses(null,
                null, null, null, testDuration, null);

        assertNotNull(filteredCourses);
        assertEquals(1, filteredCourses.size());
        verify(courseRepository).findCoursesByDefaultDuration(testDuration);
    }

    @Test
    public void testViewFilteredCoursesByCost() throws Exception {
        float testCost = 100.0f;
        List<Course> filteredCourses = courseService.viewFilteredCourses(null, null, null,
                null, null, testCost);

        assertNotNull(filteredCourses);
        assertEquals(1, filteredCourses.size());
        verify(courseRepository).findCoursesByCost(testCost);
    }

}
