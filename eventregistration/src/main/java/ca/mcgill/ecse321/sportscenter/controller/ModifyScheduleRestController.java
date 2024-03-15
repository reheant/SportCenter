package ca.mcgill.ecse321.sportscenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.sportscenter.dto.*;
import ca.mcgill.ecse321.sportscenter.model.*;
import ca.mcgill.ecse321.sportscenter.service.ModifyScheduleService;
import java.sql.Time;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/schedule/modify")
public class ModifyScheduleRestController {

	@Autowired
	private ModifyScheduleService modifyScheduleService;

    // modify a session's start and end times
    @PutMapping(value = { "/sessions/{sessionId}/time", "/sessions/{sessionId}/time/" })
    public SessionDto modifySessionTime(@PathVariable("sessionId") Integer sessionId,
                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") Time startTime,
                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") Time endTime) {

            return convertToDto(modifyScheduleService.modifySessionTime(sessionId, startTime, endTime));
    }

    // modify the course of a session
    @PutMapping(value = { "/sessions/{sessionId}/course/{courseId}", "/sessions/{sessionId}/course/{courseId}/" })
    public SessionDto modifySessionCourse(@PathVariable("sessionId") Integer sessionId,
                                          @PathVariable("courseId") Integer courseId) {
            Session s = modifyScheduleService.modifySessionCourse(sessionId, courseId);
            return convertToDto(s);
    }

     // modify the location of a session
     @PutMapping(value = { "/sessions/{sessionId}/location/{locationId}", "/sessions/{sessionId}/location/{locationId}/" })
     public SessionDto modifySessionLocation(@PathVariable("sessionId") Integer sessionId,
                                             @PathVariable("locationId") Integer locationId) {
     
            return convertToDto(modifyScheduleService.modifySessionLocation(sessionId, locationId));

     }
 
     // assign an instructor to a session
     @PutMapping(value = { "/sessions/{sessionId}/instructor/{instructorId}", "/sessions/{sessionId}/instructor/{instructorId}/" })
     public InstructorAssignmentDto assignInstructorToSession(@PathVariable("sessionId") Integer sessionId,
                                                 @PathVariable("instructorId") Integer instructorId) {
            return convertToDto(modifyScheduleService.assignInstructorToSession(sessionId, instructorId));

     }

     private SessionDto convertToDto(Session session) {
        if (session == null) {
            throw new IllegalArgumentException("There is no such Session!");
        }
        Course c = session.getCourse();
        String courseName = c.getName();
        Location l = session.getLocation();
        String locationName = l.getName();

        SessionDto dto = new SessionDto(session.getStartTime(), session.getEndTime(), courseName, locationName);
        return dto;
    }

    private InstructorAssignmentDto convertToDto(InstructorAssignment assignment) {
        if (assignment == null) {
            throw new IllegalArgumentException("There is no such Instructor Assignment!");
        }

        Instructor i = assignment.getInstructor();
        Account instructorAcc = i.getAccount();
        String instructorName = instructorAcc.getFirstName();
        Session s = assignment.getSession();
        int sessionId = s.getId();
        
        InstructorAssignmentDto dto = new InstructorAssignmentDto(instructorName,
        sessionId);
        return dto;
    }
 
}