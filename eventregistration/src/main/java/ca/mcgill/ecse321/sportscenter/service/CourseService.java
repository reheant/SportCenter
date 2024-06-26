
package ca.mcgill.ecse321.sportscenter.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportscenter.dao.CourseRepository;
import ca.mcgill.ecse321.sportscenter.model.Course;
import ca.mcgill.ecse321.sportscenter.model.Course.CourseStatus;



@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    /**
     * Retrieves a course by its name.
     * 
     * @param name The name of the course to retrieve. (String)
     * @return The course with the given name.
     * @throws Exception If the course is not found.
     */
    @Transactional
    public Course getCourseByName(String name) throws Exception {
        if (name == null) {
            throw new Exception("Could not find Course with null name " + name);
        }
        Course course = courseRepository.findCourseByName(name);

        if (course != null)
            return course;
        else
            throw new Exception("Could not find Course with name " + name);
    }

    /**
     * Retrieves a course by its name.
     * 
     * @param id The name of the course to retrieve. (String)
     * @return The course with the given name.
     * @throws Exception If the course is not found.
     */
    @Transactional
    public Course getCourseById(int id) throws Exception {
        Course course = courseRepository.findById(id).orElse(null);
        if (course != null)
            return course;
        else
            throw new Exception("Could not find Course with id" + id);
    }

    /**
     * Retrieves a list of all courses.
     *
     * @return List of all courses.
     * @throws Exception If an error occurs while retrieving the courses.
     */
    @Transactional
    public List<Course> getAllCourses() throws Exception {
        List<Course> courses = new ArrayList<>();

        courseRepository.findAll().forEach(courses::add);
        return courses;
    }

    /**
     * Retrieves a list of Course objects that match the given filtering criteria.
     * The method filters courses based on various attributes such as IDs, keyword,
     * status, requirement for an instructor, default duration, and cost. Filters
     * are applied
     * in the order listed if their corresponding parameters are not null.
     *
     * @param ids                A collection of course IDs. Only courses with IDs
     *                           in this collection will be returned.
     * @param keyword            A string to be searched for within the course name
     *                           or description.
     * @param status             The status of the courses to be returned (e.g.,
     *                           ACTIVE, INACTIVE).
     * @param requiresInstructor A Boolean indicating whether to filter courses
     *                           based on whether they require an instructor.
     * @param defaultDuration    A Float representing the default duration to filter
     *                           courses by.
     * @param cost               A Float representing the cost to filter courses by.
     * @return A list of filtered Course objects. Each course matches all provided
     *         non-null filters.
     *         If no courses match the filters, an empty list is returned.
     * @throws Exception if there is a problem processing the filters or if no
     *                   courses match the given IDs.
     */
    @Transactional
    public List<Course> viewFilteredCourses(
            Collection<Integer> ids, String keyword, CourseStatus status, Boolean requiresInstructor,
            Float defaultDuration, Float cost) throws Exception {
        List<Course> filteredCourses = new ArrayList<>();
        if (ids != null) {
            List<Course> byId = courseRepository.findCoursesByIdIn(ids);
            filteredCourses.addAll(byId);
            if (filteredCourses == null) {
                throw new Exception("No matches found.");
            }
            return filteredCourses;
        }
        if (keyword != null) {
            List<Course> byNameAndDescription = courseRepository.findCoursesByKeywordInNameOrDescription(keyword);
            filteredCourses.addAll(byNameAndDescription);
        }
        if (status != null) {
            List<Course> byStatus = courseRepository.findCoursesByCourseStatus(status);
            filteredCourses.addAll(byStatus);
        }
        if (requiresInstructor != null) {
            List<Course> byInstructor = courseRepository.findCoursesByRequiresInstructor(requiresInstructor);
            filteredCourses.addAll(byInstructor);
        }
        if (defaultDuration != null) {
            List<Course> byDuration = courseRepository.findCoursesByDefaultDuration(defaultDuration);
            filteredCourses.addAll(byDuration);
        }
        if (cost != null) {
            List<Course> byCost = courseRepository.findCoursesByCost(cost);
            filteredCourses.addAll(byCost);
        }
        if (filteredCourses == null) {
            throw new Exception("No matches found.");
        }
        return filteredCourses.stream().distinct().collect(Collectors.toList());
    }

    /**
     * Creates a new course with the specified details.
     *
     * @param name               The name of the course (String).
     * @param description        The description of the course (String).
     * @param requiresInstructor Whether the course requires an Instructor
     *                           (boolean).
     * @param duration           The duration of the course (float).
     * @param cost               The cost of the course (float).
     * @return The created course object.
     * @throws Exception If the name is invalid or already taken.
     */
    @Transactional
    public Course createCourse(String name, String description, boolean requiresInstructor, float duration, float cost)
            throws Exception {

        if (name == null || name.equals("")) {
            throw new Exception("The course requires a name");
        } else if ( courseRepository.findCourseByName(capitalize(name)) != null) {
            throw new Exception("A Course with name " + name + " already exists"); 
        } 
        
        if (description == null || description.equals("")){
            throw new Exception("The course requires a description");
        }

        if (duration <= 0) {
            throw new Exception("The duration cannot be zero or negative");
        }

        if (cost <= 0) {
            throw new Exception("The price cannot be zero or negative");
        }

        Course course = new Course();


        course.setName(capitalize(name)); 
        course.setDescription(description);
        course.setRequiresInstructor(requiresInstructor);
        course.setDefaultDuration(duration);
        course.setCost(cost);
        course.setCourseStatus(CourseStatus.Pending);
        courseRepository.save(course);

        return course;
    }

    /**
     * Approves a course with the specified name for the given owner.
     *
     * @param name the name of the course to approve
     * @return true if the course is successfully approved, false otherwise
     * @throws Exception if there is an error while approving the course
     */
    @Transactional
    public Course approveCourse(String name) throws Exception {

        if (name == null) {
            throw new Exception("Requires a name");
        }

        Course course = courseRepository.findCourseByName(name);

        if (course == null) {
            throw new Exception("A Course with name " + name + " does not exists");
        }

        // Setting Course
        course.setCourseStatus(CourseStatus.Approved);
        return courseRepository.save(course);
    }

    /**
     * Dissapproves a course with the specified name by the given owner.
     *
     * @param name the name of the course to approve
     * @return true if the course is successfully disapproved, false otherwise
     * @throws Exception if there is an error while disapproving the course
     */
    @Transactional
    public Course disapproveCourse(String name) throws Exception {

        if (name == null) {
            throw new Exception("Requires a name");
        }

        Course course = courseRepository.findCourseByName(name);
        if (course == null) {
            throw new Exception("A Course with name " + name + " does not exists");
        }

        // Setting Course
        course.setCourseStatus(CourseStatus.Refused);
        return courseRepository.save(course);
    }

    /**
     * Deletes a course from the repository based on the given course ID.
     * If the course with the specified ID does not exist, this method performs no
     * operation.
     *
     * @param courseId The ID of the course to be deleted.
     */
    public void deleteCourse(Integer courseId) {
        courseRepository.deleteById(courseId);
    }

    /** Helper Method
     * Capitalize the first letter of word in the name
     *
     * @param name The name be capitalize
     */
    private static String capitalize(String name) {
        StringBuilder capitalizedName = new StringBuilder();
        String[] words = name.toLowerCase().split("\\s+");
        
        for (String word: words){

            String capitalizedWord = word.substring(0,1).toUpperCase() + word.substring(1);
            capitalizedName.append(capitalizedWord).append(" ");

        }
        return capitalizedName.toString().trim();
    
    }
}