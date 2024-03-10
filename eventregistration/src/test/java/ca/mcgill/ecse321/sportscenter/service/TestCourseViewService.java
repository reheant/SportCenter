package ca.mcgill.ecse321.sportscenter.service;

import ca.mcgill.ecse321.sportscenter.dao.CourseRepository;

import ca.mcgill.ecse321.sportscenter.model.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;



@ExtendWith(MockitoExtension.class)
public class TestCourseViewService {
    @Mock
    private CourseRepository courseDao;
    @InjectMocks
    private CourseViewService courseViewService;
    private static final Integer COURSE_ID = 1;
    @BeforeEach
    public void setMockOutput() {
        lenient().when(courseDao.findCoursesById(any(Integer.class))).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(COURSE_ID)) {
                Course course = new Course();
                return Arrays.asList(course);
            } else {
                return new ArrayList<Course>();
            }
        });

        lenient().when(courseDao.findCoursesByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(anyString(),
                anyString())).thenAnswer((InvocationOnMock invocation) -> {
            String nameArg = invocation.getArgument(0);
            String descriptionArg = invocation.getArgument(1);
            if("SpecificName".equalsIgnoreCase(nameArg) && "SpecificDescription".equalsIgnoreCase(descriptionArg)) {
                Course specificCourse = new Course();
                specificCourse.setName(nameArg);
                specificCourse.setDescription(descriptionArg);
                return Arrays.asList(specificCourse);
            } else {
                return new ArrayList<>();
            }
        });

        lenient().when(courseDao.findCoursesByRequiresInstructor(any(Boolean.class))).thenAnswer((InvocationOnMock invocation) -> {
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

        lenient().when(courseDao.findCoursesByDefaultDuration(any(Float.class))).thenAnswer((InvocationOnMock invocation) -> {
            float defaultDuration = invocation.getArgument(0);
            List<Course> courses = new ArrayList<>();
            Course course = new Course();
            course.setName("Duration Specific Course");
            course.setDefaultDuration(defaultDuration);
            courses.add(course);
            return courses;
        });

        lenient().when(courseDao.findCoursesByCost(any(Float.class))).thenAnswer((InvocationOnMock invocation) -> {
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
    public void testViewAllCourses() {
        Course course = new Course();
        when(courseDao.findAll()).thenReturn(Arrays.asList(course));

        List<Course> courses = courseViewService.viewAllCourses();

        assertNotNull(courses);
        assertEquals(1, courses.size());
        verify(courseDao).findAll();
    }

    @Test
    public void testViewFilteredCoursesById() throws Exception {
        List<Course> filteredCourses = courseViewService.viewFilteredCourses(COURSE_ID, null, null,
                null, null, null);

        assertNotNull(filteredCourses);
        assertEquals(1, filteredCourses.size()); // Expecting the mock course to be returned
        verify(courseDao).findCoursesById(COURSE_ID);
    }

    @Test
    public void testViewFilteredCoursesByNameAndDescription() throws Exception {
        List<Course> filteredCourses = courseViewService.viewFilteredCourses(null, "SpecificName",
                "SpecificDescription", null, null, null);

        assertNotNull(filteredCourses);
        assertEquals(1, filteredCourses.size());
        verify(courseDao).findCoursesByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase("SpecificName",
                "SpecificDescription");
    }

    @Test
    public void testViewFilteredCoursesByRequiresInstructor() throws Exception {
        List<Course> filteredCourses = courseViewService.viewFilteredCourses(null, null, null,
                true, null, null);

        assertNotNull(filteredCourses);
        assertEquals(1, filteredCourses.size());
        verify(courseDao).findCoursesByRequiresInstructor(true);
    }

    @Test
    public void testViewFilteredCoursesByDefaultDuration() throws Exception {
        float testDuration = 10.0f;
        List<Course> filteredCourses = courseViewService.viewFilteredCourses(null, null, null,
                null, testDuration, null);

        assertNotNull(filteredCourses);
        assertEquals(1, filteredCourses.size());
        verify(courseDao).findCoursesByDefaultDuration(testDuration);
    }

    @Test
    public void testViewFilteredCoursesByCost() throws Exception {
        float testCost = 100.0f;
        List<Course> filteredCourses = courseViewService.viewFilteredCourses(null, null, null,
                null, null, testCost);

        assertNotNull(filteredCourses);
        assertEquals(1, filteredCourses.size());
        verify(courseDao).findCoursesByCost(testCost);
    }

}
