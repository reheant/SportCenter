package ca.mcgill.ecse321.sportscenter.dto;

public class InstructorAssignmentDto {

    private String instructorFirstName;
    private int sessionId;

    public InstructorAssignmentDto() {
    }

    public InstructorAssignmentDto( String instructor, int session) {
        this.instructorFirstName = instructor;
        this.sessionId = session;
    }

    public String getInstructor() {
        return instructorFirstName;
    }

    public void setInstructor(String instructor) {
        this.instructorFirstName = instructor;
    }

    public int getSession() {
        return sessionId;
    }

    public void setSession(int sessionId) {
        this.sessionId = sessionId;
    }
}
