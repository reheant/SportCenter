package ca.mcgill.ecse321.sportscenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.sportscenter.dto.*;
import ca.mcgill.ecse321.sportscenter.model.*;
import ca.mcgill.ecse321.sportscenter.service.ScheduleService;
import ca.mcgill.ecse321.sportscenter.controller.DtoConverter;
import java.time.LocalDateTime;

@CrossOrigin(origins = "*")
@RestController
public class ScheduleRestController {

	@Autowired
	private ScheduleService scheduleService;

    // modify a session's start and end times
    @PostMapping(value = { "/schedule/modify/sessions/{sessionId}/time", "/schedule/modify/sessions/{sessionId}/time/" })
    public ResponseEntity<SessionDto> modifySessionTime(@PathVariable("sessionId") Integer sessionId,
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime)
            throws Exception {
                SessionDto sessionDto = DtoConverter.convertToDto(scheduleService.modifySessionTime(sessionId, startTime, endTime));
                return new ResponseEntity<>(sessionDto, HttpStatus.CREATED);
    }

    // modify the course of a session
    @PostMapping(value = { "/schedule/modify/sessions/{sessionId}/course", "/schedule/modify/sessions/{sessionId}/course/" })
    public ResponseEntity<SessionDto> modifySessionCourse(@PathVariable("sessionId") Integer sessionId,
    @RequestParam(name = "courseId") Integer courseId)
                                          throws Exception {
            Session session = scheduleService.modifySessionCourse(sessionId, courseId);
            SessionDto sessionDto = DtoConverter.convertToDto(session);
            return new ResponseEntity<>(sessionDto, HttpStatus.CREATED);
    }

     // modify the location of a session
     @PostMapping(value = { "/schedule/modify/sessions/{sessionId}/location", "/schedule/modify/sessions/{sessionId}/location/" })
     public ResponseEntity<SessionDto> modifySessionLocation(@PathVariable("sessionId") Integer sessionId,
     @RequestParam(name = "locationId") Integer locationId)
                                             throws Exception {
                SessionDto sessionDto = DtoConverter.convertToDto(scheduleService.modifySessionLocation(sessionId, locationId));
                return new ResponseEntity<>(sessionDto, HttpStatus.CREATED);     
    }

    @GetMapping(value = { "/assignment", "/assignment/" })
    public InstructorAssignmentDto getAssignment(@RequestParam(name = "email") String email,
                                                 @RequestParam(name = "sessionId") Integer sessionId) throws IllegalArgumentException, NullPointerException {
        InstructorAssignment instructorAssignment = scheduleService.getAssignment(sessionId, email);
        return DtoConverter.convertToDto(instructorAssignment);
    }

     // assign an instructor to a session
     @PostMapping(value = { "/schedule/modify/sessions/{sessionId}/instructor", "/schedule/modify/sessions/{sessionId}/instructor/" })
     public ResponseEntity<InstructorAssignmentDto> assignInstructorToSession(@PathVariable("sessionId") Integer sessionId,
     @RequestParam(name = "instructorAccountEmail") String instructorAccountEmail)
                                                 throws Exception {
                InstructorAssignmentDto dto = DtoConverter.convertToDto(scheduleService.assignInstructorToSession(sessionId, instructorAccountEmail));
                return new ResponseEntity<>(dto, HttpStatus.OK);  
     }

    // unassign an instructor from a session
    @DeleteMapping(value = { "/schedule/modify/sessions/{sessionId}/instructor", "/schedule/modify/sessions/{sessionId}/instructor/" })
    public void unassignInstructorFromSession(@PathVariable("sessionId") Integer sessionId,
    @RequestParam(name = "instructorAccountEmail") String instructorAccountEmail)
                                                throws Exception {
                scheduleService.unassignInstructorFromSession(sessionId, instructorAccountEmail);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String authorized(Exception e) {
        return e.getMessage();
    }
 
}