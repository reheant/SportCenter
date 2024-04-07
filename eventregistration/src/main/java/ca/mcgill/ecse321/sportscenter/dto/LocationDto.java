package ca.mcgill.ecse321.sportscenter.dto;

import java.sql.Time;

public class LocationDto {

    private Integer id;
    private String name;
    private Integer capacity;
    private Time openingTime;
    private Time closingTime;

    public LocationDto() {
    }

    public LocationDto(Integer id, String name, Integer capacity, Time openingTime, Time closingTime) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Time getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Time openingTime) {
        this.openingTime = openingTime;
    }

    public Time getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Time closingTime) {
        this.closingTime = closingTime;
    }

}