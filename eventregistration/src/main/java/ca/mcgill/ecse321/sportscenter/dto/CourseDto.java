package ca.mcgill.ecse321.sportscenter.dto;

import ca.mcgill.ecse321.sportscenter.model.Course.CourseStatus;

public class CourseDto {
    private Integer id;
    private String name;
    private String description;
    private CourseStatus courseStatus;
    private Boolean requiresInstructor;
    private Float defaultDuration;
    private Float cost;

    public CourseDto() {
    }

    public CourseDto(Integer id, String name, String description, CourseStatus courseStatus, Boolean requiresInstructor, Float defaultDuration, Float cost){
        this.id = id;
        this.name = name;
        this.description = description;
        this.courseStatus = courseStatus;
        this.requiresInstructor = requiresInstructor;
        this.defaultDuration = defaultDuration;
        this.cost = cost;
    }

    public Integer getId() {
        return id;
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

    public Boolean getRequiresInstructor() {
        return requiresInstructor;
    }

    public Float getDefaultDuration() {
        return defaultDuration;
    }

    public Float getCost() {
        return cost;
    }

    public void setId(Integer id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCourseStatus(CourseStatus courseStatus) {
        this.courseStatus = courseStatus;
    }

    public void setRequiresInstructor(Boolean requiresInstructor) {
        this.requiresInstructor = requiresInstructor;
    }

    public void setDefaultDuration(Float defaultDuration) {
        this.defaultDuration = defaultDuration;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }
}
