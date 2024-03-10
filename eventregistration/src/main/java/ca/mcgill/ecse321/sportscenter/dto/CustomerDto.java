package ca.mcgill.ecse321.sportscenter.dto;

public class CustomerDto {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Boolean wantsEmailConfirmation ;

    public CustomerDto(String firstName, String lastName, String email, String password, Boolean wantsEmailConfirmation){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.wantsEmailConfirmation = wantsEmailConfirmation;
    }


    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public Boolean getWantsEmailConfirmation(){
        return wantsEmailConfirmation;
    }

    public void setWantsEmailConfirmation(Boolean wantsEmailConfirmation){
        this.wantsEmailConfirmation = wantsEmailConfirmation;
    }


}
