package ca.mcgill.ecse321.sportscenter.dto;

import java.time.LocalDateTime;

public class SessionDto {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String courseName;
    private String locationName;

    public SessionDto() {
    }

    public SessionDto(LocalDateTime startTime,
                      LocalDateTime endTime,
                      String courseName,
                      String locationName) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.courseName = courseName;
        this.locationName = locationName;
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

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}