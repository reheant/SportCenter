package ca.mcgill.ecse321.sportscenter.service;

import ca.mcgill.ecse321.sportscenter.dao.*;
import ca.mcgill.ecse321.sportscenter.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportscenter.model.Course.CourseStatus;

import java.sql.Time;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SportsCenterInformationService {

    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private AccountRepository accountRepository;
    
    
    @Transactional
    public Location updateCenterLocation(Integer locationId, String newName, Integer newCapacity, Time newOpeningTime, Time newClosingTime) throws Exception {
        if (locationId == null || newName == null || newCapacity == null || newOpeningTime == null || newClosingTime == null){
            throw new Exception("Please ensure all fields are complete and none are empty");
        }
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new Exception("No location found with id " + locationId));

        location.setName(newName);
        location.setCapacity(newCapacity);
        location.setOpeningTime(newOpeningTime);
        location.setClosingTime(newClosingTime);

        return locationRepository.save(location);
    }
    /**
     * 
     * @param courseId - ID of course to modify
     * @param newName - New name of course to modify
     * @param newDescription - New description of course to modify
     * @param status - New Status of course to modify
     * @param newCost   - New cost of course to modify
     * @param newDefaultDuration    - New duration of course to modify
     * @return the modifed course
     * @throws Exception
     */
    @Transactional
    public Course updateCenterCourse(Integer courseId, String newName, String newDescription, CourseStatus status, Float newCost, Float newDefaultDuration) throws Exception {
        if (courseId == null || newName == null || newDescription == null || status == null || newCost == null || newDefaultDuration ==null){
            throw new Exception("Please ensure all fields are complete and none are empty");
        }
        
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new Exception("No course found with id " + courseId));
        if(courseRepository.findCourseByName(capitalize(newName)) != null && !course.getName().equals(capitalize(newName)) ){

            throw new Exception("Course with this " + newName +" already exists");
        }
        course.setName(capitalize(newName));
        course.setDescription(newDescription);
        course.setCourseStatus(status);
        course.setCost(newCost);
        course.setDefaultDuration(newDefaultDuration);

        return courseRepository.save(course);
    }

    @Transactional
    public Instructor updateCenterInstructor(Integer instructorId, String newFirstName, String newLastName, String newEmail) throws Exception {
        if (instructorId == null || newFirstName == null || newLastName == null || newEmail == null){
            throw new Exception("Please ensure all fields are complete and none are empty");
        }
        if (!isValidName(newFirstName)) {
            throw new Exception("Invalid first name format");
        }
        if (!isValidName(newLastName)) {
            throw new Exception("Invalid last name format");
        }
        if (!isValidEmail(newEmail)) {
            throw new Exception("Invalid email format");
        }

        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new Exception("No instructor found with id " + instructorId));

        Account account = instructor.getAccount();
        account.setFirstName(newFirstName);
        account.setLastName(newLastName);
        account.setEmail(newEmail);
        accountRepository.save(account);

        instructor.setAccount(account);
        return instructorRepository.save(instructor);
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