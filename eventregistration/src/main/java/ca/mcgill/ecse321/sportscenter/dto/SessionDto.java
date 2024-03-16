package ca.mcgill.ecse321.sportscenter.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionDto {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private CourseDto course;
    private LocationDto location;

    public SessionDto() {
    }

    @JsonCreator
    public SessionDto(@JsonProperty("startTime") LocalDateTime startTime,
                      @JsonProperty("endTime") LocalDateTime endTime,
                      @JsonProperty("course") CourseDto course,
                      @JsonProperty("location") LocationDto location) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.course = course;
        this.location = location;
    }

    public LocalDateTime getStartTime() { return startTime; }

    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setStartTime(LocalDateTime startTime){
        this.startTime = startTime;
    }
    public void setEndTime(LocalDateTime endTime){
        this.endTime = endTime;
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }

    public CourseDto getCourse() {
        return course;
    }

    public void setCourse(CourseDto course) {
        this.course = course;
    }
}
