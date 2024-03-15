package ca.mcgill.ecse321.sportscenter.dto;

import java.time.LocalDateTime;

import ca.mcgill.ecse321.sportscenter.model.Course;
import ca.mcgill.ecse321.sportscenter.model.Location;
import ca.mcgill.ecse321.sportscenter.model.Registration;

public class RegistrationDto {
    private CustomerDto customer;
    private SessionDto session;    

    public RegistrationDto() {
    }

    public RegistrationDto(Registration registration){
        if (registration == null) {
            throw new NullPointerException("Registration is null.");
        }

        String firstName = registration.getCustomer().getAccount().getFirstName();
        String lastName = registration.getCustomer().getAccount().getLastName();
        String email = registration.getCustomer().getAccount().getEmail();
        String password = registration.getCustomer().getAccount().getPassword();
        Boolean wantsEmailConfirmation = registration.getCustomer().getWantsEmailConfirmation();
        this.customer = new CustomerDto(firstName, lastName, email, password, wantsEmailConfirmation);

        LocalDateTime startTime = registration.getSession().getStartTime();
        LocalDateTime endTime = registration.getSession().getEndTime();
        Location location = registration.getSession().getLocation();
        Course course = registration.getSession().getCourse();

        LocationDto locationDto = new LocationDto(location.getName(), location.getCapacity(), location.getOpeningTime(), location.getClosingTime());
        CourseDto courseDto = new CourseDto(course.getName(), course.getDescription(), course.getCourseStatus(), course.getRequiresInstructor(), course.getDefaultDuration(), course.getCost());

        this.session = new SessionDto(startTime, endTime, locationDto, courseDto);
    }

    public RegistrationDto(CustomerDto customer, SessionDto session) {
        this.customer = customer;
        this.session = session;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public SessionDto getSession() {
        return session;
    }

    public void setSession(SessionDto session) {
        this.session = session;
    }
}
