package ca.mcgill.ecse321.sportscenter.dto;

import java.sql.Time;

public class SessionDto {

    private Time startTime;
    private Time endTime;
    private String course;
    private String location;

    public SessionDto(){}

    public SessionDto(Time startTime, Time endTime, String course, String location) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.course = course;
    this.location = location;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getCourse() {
        return this.course;
    }

    public void setCourseId(String course) {
        this.course = course;
    }

    public String getLocationId() {
        return location;
    }

    public void setLocationId(String location) {
        this.location = location;
    }

}