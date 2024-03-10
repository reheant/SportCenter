
package ca.mcgill.ecse321.sportscenter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportscenter.dao.CourseRepository;
import ca.mcgill.ecse321.sportscenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Course;
import ca.mcgill.ecse321.sportscenter.model.Owner;

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
       Course course = courseRepository.findCourseByName(name);

       if (course != null) return course;
       else throw new Exception("Could not find Couse with name " + name);
        
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
    * @param isApproved Whether the course is approved (boolean).
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

        return course;
    }

    /**
    * Creates a new owner with the provided details.
    *
    * @param firstName the first name of the owner
    * @param lastName the last name of the owner
    * @param email the email address of the owner
    * @param password the password for the owner's account
    * @return the newly created owner
    * @throws Exception if there is an error creating the owner
    */
    @Transactional
    public Owner createOwner(String firstName, String lastName, String email, String password) throws Exception {        
    
        if (firstName == null|| lastName==null || email==null || password==null){
            throw new IllegalArgumentException("Please ensure all fields are complete and none are empty");
        }
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (!isValidName(firstName)) {
            throw new IllegalArgumentException("Invalid first name format");
        }

        if (!isValidName(lastName)) {
            throw new IllegalArgumentException("Invalid last name format");
        }

        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Invalid password format, password must have at least: one lower case letter, one higher case letter, one digit, one special character and be 8 charcters minimum");
        }

        if (accountRepository.findAccountByEmail(email) != null) {
            throw new Exception("Email is already in use");
        }
        // Create Account
        Account ownerAccount = new Account();
        ownerAccount.setEmail(email);
        ownerAccount.setFirstName(firstName);
        ownerAccount.setLastName(lastName);
        ownerAccount.setPassword(password);
        accountRepository.save(ownerAccount);
        
        // Create Owner
        Owner owner = new Owner();
        owner.setAccount(ownerAccount); 
        ownerRepository.save(owner);
        return owner;
    }

    /**
    * Approves a course with the specified name for the given owner. 
    *
    * @param name the name of the course to approve
    * @param owner the owner that approves the course
    * @return true if the course is successfully approved, false otherwise
    * @throws Exception if there is an error while approving the course
    */
    @Transactional
    public boolean approveCourse(String name, Owner owner) throws Exception {
        if (name == null) {
            throw new IllegalArgumentException("Requires a name");
        }
        
        Course course = courseRepository.findCourseByName(name); 
        if (course == null){
            throw new Exception("A Course with name " + name + " does not exists");
        } 
        if (ownerRepository.findOwnerByEmail(owner.getAccount().getEmail()) == null){
            throw new Exception("The following admin " + owner + " does not exists");
        }
        // Setting Course 
        course.setIsApproved(true);
        return true;
    }

    /**
    * Dissapproves a course with the specified name by the given owner. 
    *
    * @param name the name of the course to approve
    * @param owner the owner that disapproves the course
    * @return true if the course is successfully disapproved, false otherwise
    * @throws Exception if there is an error while disapproving the course
    */
    @Transactional
    public boolean disapproveCourse(String name, Owner owner) throws Exception {

        if (name == null) {
            throw new IllegalArgumentException("Requires a name");
        }
        
        Course course = courseRepository.findCourseByName(name); 
        if (course == null){
            throw new Exception("A Course with name " + name + " does not exists");
        }
        if (ownerRepository.findOwnerByEmail(owner.getAccount().getEmail()) == null){
            throw new Exception("The following admin " + owner + " does not exists");
        }
        // Setting Course 
        course.setIsApproved(false);
        return true;
    }  

    /** Helper Method
     * Respecting RFC 5322 email format (source : https://www.javatpoint.com/java-email-validation#:~:text=To%20validate%20the%20email%20permitted,%5D%2B%24%22%20regular%20expression.)
     * 
     * @param email the email to verify
     * @return true if the email is valid, false otherwise
     */
    private boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    /** Helper Method
     * Regex respects basic name formats, including names like "Louis-Phillipe" or "Henry Jr." (allows Hyphens and periods)
     * 
     * @param name the name to verify
     * @return true if the name is valid, false otherwise
     */
    private boolean isValidName(String name) {
        String regex = "^[a-z ,.'-]+$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    /** Helper Method
     * Password requirements: AT LEAST: one upper case letter, one lower case letter, one digit, one special character, minimum 8 character length
     * 
     * @param password the password to verify
     * @return true if the password is valid, false otherwise
     */
    private boolean isValidPassword(String password) {
        String regex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}