
package ca.mcgill.ecse321.sportscenter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportscenter.dao.CourseRepository;
import ca.mcgill.ecse321.sportscenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Course;
import ca.mcgill.ecse321.sportscenter.model.Owner;
import ca.mcgill.ecse321.sportscenter.model.Course.CourseStatus;

@Service
public class CourseService {

    @Autowired 
    CourseRepository courseRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired 
    OwnerRepository ownerRepository;
    
    /**
    * Retrieves a course by its ID.
    *
    * @param id The ID of the course to retrieve. (Integer)
    * @return The course with the specified ID.
    * @throws Exception If the course is not found.
    */
    @Transactional
    public Course getCourseById(Integer id) throws Exception {
       Course course = courseRepository.findById(id).orElse(null);

       if (course != null) return course;
       else throw new Exception("Could not find Couse with name " + id);
        
    }

    /**
    * Retrieves a course by its name.
    * 
    * @param name The name of the course to retrieve. (String)
    * @return The course with the given name.
    * @throws Exception If the course is not found.
    */
    @Transactional
    public Course getCourseByName(String name) throws Exception {
        if (name == null){
            throw new Exception("Could not find Course with null name " + name);
        }
        Course course = courseRepository.findCourseByName(name);

        if (course != null) return course;
        else throw new Exception("Could not find Course with name " + name);   
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
    * Creates a new course with the specified details.
    *
    * @param name The name of the course (String).
    * @param description The description of the course (String).
    * @param courseStatus Current Status of the course.
    * @param requiresInstructor Whether the course requires an Instructor (boolean).
    * @param duration The duration of the course (float).
    * @param cost The cost of the course (float).
    * @return The created course object.
    * @throws Exception If the name is invalid or already taken.
    */
    @Transactional
    public Course createCourse(String name, String description, boolean requiresInstructor, float duration, float cost) throws Exception {
        
        if (name == null || name.equals("")) {
            throw new Exception("The course requires a name");
        } else if ( courseRepository.findCourseByName(name) != null) {
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

        course.setName(name); 
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
    * @param email the owner that approves the course
    * @return true if the course is successfully approved, false otherwise
    * @throws Exception if there is an error while approving the course
    */
    @Transactional
    public Course approveCourse(String name, String email) throws Exception {
        if (email == null){
            throw new IllegalArgumentException("email is null");
        }
        if (name == null) {
            throw new IllegalArgumentException("Requires a name");
        }
        
        Account ownerAccount = accountRepository.findAccountByEmail(email);
        List<Owner> existingOwners = ownerRepository.findAll();
        if(ownerAccount == null){
            throw new Exception("Email is not accociated to an account");
        }
        boolean matchFound = false;
        for (Owner owner : existingOwners) {
            if (owner.getAccount().equals(ownerAccount)) {
                matchFound = true;
                break;
            }
        }
        if (!matchFound) {
            throw new IllegalArgumentException("Owner with email " + email + " was not found.");
        }

        Course course = courseRepository.findCourseByName(name); 
        if (course == null){
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
    * @param email the owner that disapproves the course
    * @return true if the course is successfully disapproved, false otherwise
    * @throws Exception if there is an error while disapproving the course
    */
    @Transactional
    public Course disapproveCourse(String name, String email) throws Exception {

        if (email == null){
            throw new IllegalArgumentException("email is null");
        }
        if (name == null) {
            throw new IllegalArgumentException("Requires a name");
        }


        Account ownerAccount = new Account();
        ownerAccount = accountRepository.findAccountByEmail(email);
        List<Owner> existingOwners = ownerRepository.findAll();
        if(ownerAccount == null){

            throw new Exception("Email is not accociated to an account");
        }
        boolean matchFound = false;
        for (Owner owner : existingOwners) {
            if (owner.getAccount().equals(ownerAccount)) {
                matchFound = true;
                break;
            }
        }
        if (!matchFound) {
            throw new IllegalArgumentException("Owner with email " + email + " was not found.");
        }

        Course course = courseRepository.findCourseByName(name); 
        if (course == null){
            throw new Exception("A Course with name " + name + " does not exists");
        }
        
        // Setting Course 
        course.setCourseStatus(CourseStatus.Refused);
        return courseRepository.save(course);
    }  
}