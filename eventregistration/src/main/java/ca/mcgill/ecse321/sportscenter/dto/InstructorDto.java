package ca.mcgill.ecse321.sportscenter.dto;

public class InstructorDto {

    private String firstName;

    public InstructorDto() {
    }
    
    public InstructorDto(String firstName){
        this.firstName = firstName;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
}
