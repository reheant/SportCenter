package ca.mcgill.ecse321.sportscenter.dto;

import ca.mcgill.ecse321.sportscenter.model.Course.CourseStatus;

public class CourseDto {
  private String name;
  private String description;
  private CourseStatus courseStatus;
  private boolean requiresInstructor;
  private float defaultDuration;
  private float cost;


    public CourseDto(String name, String description, CourseStatus courseStatus, boolean requiresInstructor, float defaultDuration, float cost) {
        this.name = name;
        this.description = description;
        this.courseStatus = courseStatus;
        this.requiresInstructor = requiresInstructor;
        this.defaultDuration = defaultDuration;
        this.cost = cost;
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

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public CourseStatus getCourseStatus(){
        return courseStatus;
    }

    public boolean getRequiresInstructor(){
        return requiresInstructor;
    }

    public float getDefaultDuration(){
        return defaultDuration;
    }

    public float getCost(){
        return cost;
    }
}