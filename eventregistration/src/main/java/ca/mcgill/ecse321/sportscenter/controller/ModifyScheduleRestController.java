package ca.mcgill.ecse321.sportscenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.sportscenter.dto.*;
import ca.mcgill.ecse321.sportscenter.model.*;
import ca.mcgill.ecse321.sportscenter.service.ModifyScheduleService;
import java.time.LocalDateTime;

@CrossOrigin(origins = "*")
@RestController
public class ModifyScheduleRestController {

	@Autowired
	private ModifyScheduleService modifyScheduleService;

    // modify a session's start and end times
    @PostMapping(value = { "/schedule/modify/sessions/{sessionId}/time", "/schedule/modify/sessions/{sessionId}/time/" })
    public ResponseEntity<SessionDto> modifySessionTime(@PathVariable("sessionId") Integer sessionId,
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime)
            throws Exception {
                SessionDto sessionDto = convertToDto(modifyScheduleService.modifySessionTime(sessionId, startTime, endTime));
                return new ResponseEntity<>(sessionDto, HttpStatus.CREATED);
    }

    // modify the course of a session
    @PostMapping(value = { "/schedule/modify/sessions/{sessionId}/course", "/schedule/modify/sessions/{sessionId}/course/" })
    public ResponseEntity<SessionDto> modifySessionCourse(@PathVariable("sessionId") Integer sessionId,
    @RequestParam(name = "courseId") Integer courseId)
                                          throws Exception {
            Session session = modifyScheduleService.modifySessionCourse(sessionId, courseId);
            SessionDto sessionDto = convertToDto(session);
            return new ResponseEntity<>(sessionDto, HttpStatus.CREATED);
    }

     // modify the location of a session
     @PostMapping(value = { "/schedule/modify/sessions/{sessionId}/location", "/schedule/modify/sessions/{sessionId}/location/" })
     public ResponseEntity<SessionDto> modifySessionLocation(@PathVariable("sessionId") Integer sessionId,
     @RequestParam(name = "locationId") Integer locationId)
                                             throws Exception {
                SessionDto sessionDto = convertToDto(modifyScheduleService.modifySessionLocation(sessionId, locationId));
                return new ResponseEntity<>(sessionDto, HttpStatus.CREATED);     
    }
 
     // assign an instructor to a session
     @PostMapping(value = { "/schedule/modify/sessions/{sessionId}/instructor", "/schedule/modify/sessions/{sessionId}/instructor/" })
     public ResponseEntity<InstructorAssignmentDto> assignInstructorToSession(@PathVariable("sessionId") Integer sessionId,
     @RequestParam(name = "instructorId") Integer instructorId)
                                                 throws Exception {
                InstructorAssignmentDto dto = convertToDto(modifyScheduleService.assignInstructorToSession(sessionId, instructorId));
                return new ResponseEntity<>(dto, HttpStatus.CREATED);  
     }

     private SessionDto convertToDto(Session session) throws Exception {
        if (session == null) {
            throw new Exception("There is no such Session!");
        }
 
        Course c = session.getCourse();
        String courseName = c.getName();
        Location l = session.getLocation();
        String locationName = l.getName();

        SessionDto dto = new SessionDto(session.getStartTime(), session.getEndTime(), courseName, locationName);
        return dto;
 
    }

    private InstructorAssignmentDto convertToDto(InstructorAssignment assignment) throws Exception {
        if (assignment == null) {
            throw new Exception("There is no such Instructor Assignment!");
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
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String authorized(Exception e) {
        return e.getMessage();
    }
 
}