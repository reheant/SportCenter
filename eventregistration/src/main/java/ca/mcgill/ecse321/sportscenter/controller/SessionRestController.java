package ca.mcgill.ecse321.sportscenter.controller;

import ca.mcgill.ecse321.sportscenter.dto.SessionDto;
import ca.mcgill.ecse321.sportscenter.model.Session;
import ca.mcgill.ecse321.sportscenter.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
public class SessionRestController {
    @Autowired
    private SessionService sessionService;

    @PostMapping(value = { "/session", "/session/" })
    public SessionDto createSession(@RequestParam(name = "startTime") LocalDateTime startTime,
                                    @RequestParam(name = "endTime") LocalDateTime endtime,
                                    @RequestParam(name = "courseName") String courseName,
                                    @RequestParam(name = "locationName") String locationName) throws Exception {
        Session session = sessionService.createSession(startTime, endtime, courseName, locationName);
        return DtoConverter.convertToDto(session);
    }

    @GetMapping(value = { "/sessions", "/sessions/" })
    public List<SessionDto> getAllSessions() {
        List<SessionDto> sessionDtoList = new ArrayList<>();

        try {
            for (Session s: sessionService.getAllSessions()){
                sessionDtoList.add(DtoConverter.convertToDto(s));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionDtoList;
    }

    @GetMapping(value = { "/sessions/filter", "/sessions/filter/"})
    public List<SessionDto> viewFilteredSessions(
            @RequestParam(value="ids", required=false) Collection<Integer> ids,
            @RequestParam(value="name", required=false) String name,
            @RequestParam(value="date", required=false) LocalDate date,
            @RequestParam(value="duration", required=false) Float duration,
            @RequestParam(value="instructor", required=false) String instructor) throws Exception {
        if (ids == null && name == null && date == null && duration == null && instructor == null) {
            return getAllSessions();
        }
        return sessionService.viewFilteredSessions(ids, name, date, duration, instructor).stream().map(p -> DtoConverter.convertToDto(p)).collect(Collectors.toList());
    }

    @DeleteMapping(value = {"/sessions/{id}", "/sessions/{id}/"})
    public void deleteSession(@PathVariable Integer id) throws Exception {
        sessionService.deleteSession(id);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String authorized(Exception e) {
        return e.getMessage();
    }
}
