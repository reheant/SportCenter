package ca.mcgill.ecse321.sportscenter.controller;

import ca.mcgill.ecse321.sportscenter.dto.AccountDto;
import ca.mcgill.ecse321.sportscenter.dto.CardDto;
import ca.mcgill.ecse321.sportscenter.dto.CourseDto;
import ca.mcgill.ecse321.sportscenter.dto.CustomerDto;
import ca.mcgill.ecse321.sportscenter.dto.InstructorDto;
import ca.mcgill.ecse321.sportscenter.dto.OwnerDto;
import ca.mcgill.ecse321.sportscenter.dto.LocationDto;
import ca.mcgill.ecse321.sportscenter.dto.PaypalDto;
import ca.mcgill.ecse321.sportscenter.dto.SessionDto;
import ca.mcgill.ecse321.sportscenter.dto.RegistrationDto;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Card;
import ca.mcgill.ecse321.sportscenter.model.Course;
import ca.mcgill.ecse321.sportscenter.model.Customer;
import ca.mcgill.ecse321.sportscenter.model.Instructor;
import ca.mcgill.ecse321.sportscenter.model.Location;
import ca.mcgill.ecse321.sportscenter.model.Owner;
import ca.mcgill.ecse321.sportscenter.model.PayPal;
import ca.mcgill.ecse321.sportscenter.model.Session;
import ca.mcgill.ecse321.sportscenter.model.Registration;

public class DtoConverter {
    public static AccountDto convertToDto(Account account) {
        if (account == null){
            throw new NullPointerException("Account cannot be null.");
        }
        AccountDto accountDto = new AccountDto(account.getFirstName(), account.getEmail(), account.getLastName(), account.getPassword());
        return accountDto;
    }

    public static OwnerDto convertToDto(Owner owner) {
        if (owner == null){
            throw new NullPointerException("Owner cannot be null.");
        }
        OwnerDto ownerDto = new OwnerDto(owner.getAccount().getEmail());
        return ownerDto;
    }

    public static InstructorDto convertToDto(Instructor instructor) {
        if (instructor == null){
            throw new NullPointerException("Instructor cannot be null.");
        }
        InstructorDto instructorDto = new InstructorDto(instructor.getAccount().getEmail());
        return instructorDto;
    }

    public static CustomerDto convertToDto(Customer customer) {
        if (customer == null){
            throw new NullPointerException("Customer cannot be null.");
        }
        CustomerDto customerDto = new CustomerDto(customer.getAccount().getEmail(), customer.getWantsEmailConfirmation());
        return customerDto;
    }

    public static PaypalDto convertToDto(PayPal paypal) {
        if (paypal == null){
            throw new NullPointerException("Paypal cannot be null.");
        }
        PaypalDto paypalDto = new PaypalDto(paypal.getName(), paypal.getEmail(), paypal.getPassword(), paypal.getCustomer().getAccount().getEmail());
        return paypalDto;
    }

    public static CardDto convertToDto(Card card) {
        if (card == null){
            throw new NullPointerException("Card cannot be null.");
        }
        CardDto cardDto = new CardDto(card.getName(), card.getPaymentCardType(), card.getNumber(), card.getExpirationDate(), card.getCcv(), card.getCustomer().getAccount().getEmail());
        return cardDto;
    }

    public static CourseDto convertToDto(Course course) {
        if (course == null) {
            throw new NullPointerException("Course cannot be null.");
        }
        CourseDto courseDto = new CourseDto(course.getName(), course.getDescription(), course.getCourseStatus(), course.getRequiresInstructor(), course.getDefaultDuration(), course.getCost());
        return courseDto;
    }

    public static LocationDto convertToDto(Location location) {
        if (location == null) {
            throw new NullPointerException("Location cannot be null.");
        }
        LocationDto locationDto = new LocationDto(location.getName(), location.getCapacity(), location.getOpeningTime(), location.getClosingTime());
        return locationDto;
    }

    public static SessionDto convertToDto(Session session) {
        if (session == null) {
            throw new NullPointerException("Session cannot be null.");
        }
        CourseDto courseDto = convertToDto(session.getCourse());
        LocationDto locationDto = convertToDto(session.getLocation());
        SessionDto sessionDto = new SessionDto(session.getStartTime(), session.getEndTime(), courseDto, locationDto);
        return sessionDto;
    }

    public static RegistrationDto convertToDto(Registration registration) {
        if (registration == null) {
            throw new NullPointerException("Registration cannot be null.");
        }
        CustomerDto customerDto = convertToDto(registration.getCustomer());
        SessionDto sessionDto = convertToDto(registration.getSession());
        RegistrationDto registrationDto = new RegistrationDto(customerDto, sessionDto);
        return registrationDto;
    }

}
