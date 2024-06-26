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
    public InstructorAssignment assignInstructorToSession(Integer sessionId, String instructorAccountEmail) throws Exception {
        if (sessionId == null || instructorAccountEmail == null){
            throw new Exception("Please ensure all fields are complete and none are empty");
        }

        Session session = sessionRepository.findById(sessionId)
        .orElseThrow(() -> new Exception("No session found with id " + sessionId));
        
        Instructor instructor = instructorRepository.findInstructorByAccountEmail(instructorAccountEmail);

        if (instructor == null) {
            throw new NullPointerException("No instructor found with email " + instructorAccountEmail);
        }

        InstructorAssignment duplicateAssignment = instructorAssignmentRepository.findInstructorAssignmentByInstructorAccountEmailAndSessionId(instructorAccountEmail, sessionId);
        if (duplicateAssignment != null) {
            throw new IllegalArgumentException("Instructor with email " + instructorAccountEmail + " is already assigned to session with id " + sessionId);
        }
        
        InstructorAssignment instructorAssignment = new InstructorAssignment();
        instructorAssignment.setInstructor(instructor);
        instructorAssignment.setSession(session);

        return instructorAssignmentRepository.save(instructorAssignment);
        
    }

    @Transactional
    public InstructorAssignment getAssignment(Integer sessionId, String instructorAccountEmail) throws NullPointerException, IllegalArgumentException {
        if (sessionId == null || instructorAccountEmail == null){
            throw new NullPointerException("Please ensure all fields are complete and none are empty");
        }

        Session session = sessionRepository.findById(sessionId)
        .orElseThrow(() -> new IllegalArgumentException("No session found with id " + sessionId));
        
        Instructor instructor = instructorRepository.findInstructorByAccountEmail(instructorAccountEmail);

        if (instructor == null) {
            throw new IllegalArgumentException("No instructor found with email " + instructorAccountEmail);
        }

        InstructorAssignment assignment = instructorAssignmentRepository.findInstructorAssignmentByInstructorAccountEmailAndSessionId(instructorAccountEmail, sessionId);

        return assignment;
    }

    @Transactional
    public Boolean unassignInstructorFromSession(Integer sessionId, String instructorAccountEmail) throws NullPointerException{
        if (sessionId == null){
            throw new NullPointerException("The session id cannot be null.");
        }

        if (instructorAccountEmail == null) {
            throw new NullPointerException("The instructor account email cannot be null.");
        }

        InstructorAssignment instructorAssignment = instructorAssignmentRepository.findInstructorAssignmentByInstructorAccountEmailAndSessionId(instructorAccountEmail, sessionId);

        if (instructorAssignment == null) {
            throw new IllegalArgumentException("No matching instructor assignment.");
        }

        instructorAssignmentRepository.deleteById(instructorAssignment.getId());
        return true;
    }

}




