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
	private ScheduleService modifyScheduleService;

    // modify a session's start and end times
    @PostMapping(value = { "/schedule/modify/sessions/{sessionId}/time", "/schedule/modify/sessions/{sessionId}/time/" })
    public ResponseEntity<SessionDto> modifySessionTime(@PathVariable("sessionId") Integer sessionId,
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime)
            throws Exception {
                SessionDto sessionDto = DtoConverter.convertToDto(modifyScheduleService.modifySessionTime(sessionId, startTime, endTime));
                return new ResponseEntity<>(sessionDto, HttpStatus.CREATED);
    }

    // modify the course of a session
    @PostMapping(value = { "/schedule/modify/sessions/{sessionId}/course", "/schedule/modify/sessions/{sessionId}/course/" })
    public ResponseEntity<SessionDto> modifySessionCourse(@PathVariable("sessionId") Integer sessionId,
    @RequestParam(name = "courseId") Integer courseId)
                                          throws Exception {
            Session session = modifyScheduleService.modifySessionCourse(sessionId, courseId);
            SessionDto sessionDto = DtoConverter.convertToDto(session);
            return new ResponseEntity<>(sessionDto, HttpStatus.CREATED);
    }

     // modify the location of a session
     @PostMapping(value = { "/schedule/modify/sessions/{sessionId}/location", "/schedule/modify/sessions/{sessionId}/location/" })
     public ResponseEntity<SessionDto> modifySessionLocation(@PathVariable("sessionId") Integer sessionId,
     @RequestParam(name = "locationId") Integer locationId)
                                             throws Exception {
                SessionDto sessionDto = DtoConverter.convertToDto(modifyScheduleService.modifySessionLocation(sessionId, locationId));
                return new ResponseEntity<>(sessionDto, HttpStatus.CREATED);     
    }
 
     // assign an instructor to a session
     @PostMapping(value = { "/schedule/modify/sessions/{sessionId}/instructor", "/schedule/modify/sessions/{sessionId}/instructor/" })
     public ResponseEntity<InstructorAssignmentDto> assignInstructorToSession(@PathVariable("sessionId") Integer sessionId,
     @RequestParam(name = "instructorId") Integer instructorId)
                                                 throws Exception {
                InstructorAssignmentDto dto = DtoConverter.convertToDto(modifyScheduleService.assignInstructorToSession(sessionId, instructorId));
                return new ResponseEntity<>(dto, HttpStatus.CREATED);  
     }

    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String authorized(Exception e) {
        return e.getMessage();
    }
 
}