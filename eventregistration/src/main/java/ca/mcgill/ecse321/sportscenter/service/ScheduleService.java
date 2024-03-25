package ca.mcgill.ecse321.sportscenter.service;

import ca.mcgill.ecse321.sportscenter.dao.*;
import ca.mcgill.ecse321.sportscenter.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ScheduleService {

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private InstructorAssignmentRepository instructorAssignmentRepository;

    @Transactional
    public Session modifySessionTime(Integer sessionId, LocalDateTime newStartTime, LocalDateTime newEndTime) throws Exception {
        if (sessionId == null || newStartTime == null || newEndTime == null){
            throw new Exception("Please ensure all fields are complete and none are empty");
        }

        Session session = sessionRepository.findById(sessionId)
        .orElseThrow(() -> new Exception("No session found with id " + sessionId));

        session.setStartTime(newStartTime);
        session.setEndTime(newEndTime);
        sessionRepository.save(session);
        return session;
    }

    @Transactional
    public Session modifySessionCourse(Integer sessionId, Integer courseId) throws Exception {
        if (sessionId == null || courseId == null){
            throw new Exception("Please ensure all fields are complete and none are empty");
        }

        Session session = sessionRepository.findById(sessionId)
        .orElseThrow(() -> new Exception("No session found with id " + sessionId));
        
        Course course = courseRepository.findById(courseId)
        .orElseThrow(() -> new Exception("No course found with id " + courseId));
        
        session.setCourse(course);
        sessionRepository.save(session);
        return session;
    }

    @Transactional
    public Session modifySessionLocation(Integer sessionId, Integer locationId) throws Exception {
        if (sessionId == null || locationId == null){
            throw new Exception("Please ensure all fields are complete and none are empty");
        }
        Session session = sessionRepository.findById(sessionId)
        .orElseThrow(() -> new Exception("No session found with id " + sessionId));
        
        Location location = locationRepository.findById(locationId)
        .orElseThrow(() -> new Exception("No location found with id " + locationId));
        
        session.setLocation(location);
        sessionRepository.save(session);
        return session;
    }

    @Transactional
    public InstructorAssignment assignInstructorToSession(Integer sessionId, Integer instructorId) throws Exception {
        if (sessionId == null || instructorId == null){
            throw new Exception("Please ensure all fields are complete and none are empty");
        }
        Session session = sessionRepository.findById(sessionId)
        .orElseThrow(() -> new Exception("No session found with id " + sessionId));
        
        Instructor instructor = instructorRepository.findById(instructorId)
        .orElseThrow(() -> new Exception("No instructor found with id " + instructorId));

        InstructorAssignment instructorAssignment = new InstructorAssignment();
        instructorAssignment.setInstructor(instructor);
        instructorAssignment.setSession(session);

        return instructorAssignmentRepository.save(instructorAssignment);
        
    }

}




