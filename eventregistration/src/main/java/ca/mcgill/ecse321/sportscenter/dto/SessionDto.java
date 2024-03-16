package ca.mcgill.ecse321.sportscenter.dto;

import java.time.LocalDateTime;

public class SessionDto {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    public SessionDto(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
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
}
