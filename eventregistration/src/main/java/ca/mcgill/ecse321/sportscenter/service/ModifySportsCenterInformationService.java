package ca.mcgill.ecse321.sportscenter.service;

import ca.mcgill.ecse321.sportscenter.dao.*;
import ca.mcgill.ecse321.sportscenter.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportscenter.model.Course.CourseStatus;

import java.sql.Time;

@Service
public class ModifySportsCenterInformationService {

    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private InstructorRepository instructorRepository;

    @Transactional
    public Location updateCenterLocation(Integer locationId, String newName, Integer newCapacity, Time newOpeningTime, Time newClosingTime) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("No location found with id " + locationId));

        location.setName(newName);
        location.setCapacity(newCapacity);
        location.setOpeningTime(newOpeningTime);
        location.setClosingTime(newClosingTime);

        return locationRepository.save(location);
    }

    @Transactional
    public Course updateCenterCourse(Integer courseId, String newName, String newDescription, CourseStatus status, Float newCost, Float newDefaultDuration) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("No course found with id " + courseId));

        course.setName(newName);
        course.setDescription(newDescription);
        course.setCourseStatus(status);
        course.setCost(newCost);
        course.setDefaultDuration(newDefaultDuration);

        return courseRepository.save(course);
    }

    @Transactional
    public Instructor updateCenterInstructor(Integer instructorId, String newFirstName, String newLastName, String newEmail) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("No instructor found with id " + instructorId));

        Account account = instructor.getAccount();
        account.setFirstName(newFirstName);
        account.setLastName(newLastName);
        account.setEmail(newEmail);

        instructor.setAccount(account);
        return instructorRepository.save(instructor);
    }

}