package ca.mcgill.ecse321.sportscenter.dto;

import ca.mcgill.ecse321.sportscenter.model.Course;
import ca.mcgill.ecse321.sportscenter.model.Course.CourseStatus;

public class CourseDto {
    private Integer id;
    private String name;
    private String description;
    private Course.CourseStatus courseStatus;
    private Boolean isApproved;
    private Boolean requiresInstructor;
    private Float defaultDuration;
    private Float cost;
    public CourseDto(String name, String description, Boolean isApproved, Boolean requiresInstructor, Float defaultDuration, Float cost){
        this.name = name;
        this.description = description;
        this.courseStatus = courseStatus;
        this.isApproved = isApproved;
        this.requiresInstructor = requiresInstructor;
        this.defaultDuration = defaultDuration;
        this.cost = cost;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public CourseStatus getCourseStatus() {
        return courseStatus;
    }
    public Boolean getIsApproved() {
        return isApproved;
    }
    public Boolean getRequiresInstructor() {
        return requiresInstructor;
    }
    public Float getDefaultDuration() {
        return defaultDuration;
    }
    public Float getCost() {
        return cost;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setCourseStatus(CourseStatus courseStatus){
        this.courseStatus = courseStatus;
    }
    public void setRequiresInstructor(boolean requiresInstructor){
        this.requiresInstructor = requiresInstructor;
    }
    public void setDefaultDuration(float defaultDuration){
        this.defaultDuration = defaultDuration;
    }
    public void setCost(float cost){
        this.cost = cost;
    }
}


