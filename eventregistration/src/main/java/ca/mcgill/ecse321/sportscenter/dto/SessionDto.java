package ca.mcgill.ecse321.sportscenter.dto;

import java.time.LocalDateTime;

public class SessionDto {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private CourseDto course;
    private LocationDto location;

    public SessionDto(LocalDateTime startTime, LocalDateTime endTime, LocationDto location, CourseDto course) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.course = course;
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
