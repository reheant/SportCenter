package ca.mcgill.ecse321.sportscenter.controller;

import ca.mcgill.ecse321.sportscenter.dto.SessionDto;
import ca.mcgill.ecse321.sportscenter.model.Instructor;
import ca.mcgill.ecse321.sportscenter.model.Session;
import ca.mcgill.ecse321.sportscenter.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return convertToDto(session);
    }

    @GetMapping(value = { "/sessions", "/sessions/" })
    public List<SessionDto> getAllSessions() {
        List<SessionDto> sessionDtoList = new ArrayList<>();

        try {
            for (Session c: sessionService.getAllSessions()){
                sessionDtoList.add(convertToDto(c));
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
            @RequestParam(value="instructorName", required=false) String instructorName) throws Exception {
        if (ids == null && name == null && date == null && duration == null && instructorName == null) {
            return getAllSessions();
        }
        return sessionService.viewFilteredSessions(ids, name, date, duration, instructorName).stream().map(p -> convertToDto(p)).collect(Collectors.toList());
    }

    @DeleteMapping(value = {"/sessions/{id}", "/sessions/{id}/"})
    public void deleteSession(@PathVariable Integer id) {
        sessionService.deleteSession(id);
    }

    private SessionDto convertToDto(Session s){
        if (s == null) {
            throw new IllegalArgumentException("There is no such session");
        }
        SessionDto sessionDto = new SessionDto(s.getStartTime(), s.getEndTime());
        return sessionDto;
    }
}
