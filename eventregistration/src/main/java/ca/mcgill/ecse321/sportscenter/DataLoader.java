package ca.mcgill.ecse321.sportscenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportscenter.dao.CardRepository;
import ca.mcgill.ecse321.sportscenter.dao.CourseRepository;
import ca.mcgill.ecse321.sportscenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportscenter.dao.InstructorAssignmentRepository;
import ca.mcgill.ecse321.sportscenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportscenter.dao.LocationRepository;
import ca.mcgill.ecse321.sportscenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportscenter.dao.PayPalRepository;
import ca.mcgill.ecse321.sportscenter.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportscenter.dao.SessionRepository;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Card;
import ca.mcgill.ecse321.sportscenter.model.Course;
import ca.mcgill.ecse321.sportscenter.model.Customer;
import ca.mcgill.ecse321.sportscenter.model.Instructor;
import ca.mcgill.ecse321.sportscenter.model.InstructorAssignment;
import ca.mcgill.ecse321.sportscenter.model.Location;
import ca.mcgill.ecse321.sportscenter.model.Owner;
import ca.mcgill.ecse321.sportscenter.model.PayPal;
import ca.mcgill.ecse321.sportscenter.model.Registration;
import ca.mcgill.ecse321.sportscenter.model.Session;
import ca.mcgill.ecse321.sportscenter.model.Card.PaymentCardType;
import ca.mcgill.ecse321.sportscenter.model.Course.CourseStatus;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.sql.Time;

@Component
public class DataLoader implements ApplicationRunner {

        @Autowired
        private AccountRepository account;

        @Autowired
        private CustomerRepository customer;

        @Autowired
        private OwnerRepository owner;

        @Autowired
        private PayPalRepository paypal;

        @Autowired
        private CardRepository card;

        @Autowired
        private LocationRepository location;

        @Autowired
        private InstructorRepository instructor;

        @Autowired
        private CourseRepository course;

        @Autowired
        private SessionRepository session;

        @Autowired
        private RegistrationRepository reg;

        @Autowired
        private InstructorAssignmentRepository ia;

        @Value("${load-data}")
        private String loadData;

        private Account[] accounts = {
                        new Account("Tiffany", "Miller", "tiffany.miller@example.com", "Pa$$w0rd"),
                        new Account("Anastasiia", "Nemyrovska", "anastasiia.nemyrovska@mail.mcgill.ca", "Pa$$w0rd"),
                        new Account("Michael", "Johnson", "michael.johnson@example.com", "M1ch @elJ0hnson!"),
                        new Account("Emily", "Wilson", "emily.wilson@example.com", "Em1lyW!ls0n"),
                        new Account("David", "Brown", "david.brown@example.com", "D @v1dBrown!"),
                        new Account("Jesus", "Christ", "admin@example.com", "J3susChr!st")
        };

        private String[][] paypals = {
                        { "Paypal 1", "1", "asdja@b.com", "definitelyextremelystrongpassword" },
        };

        private String[][] cards = {
                        { "Card 1", "2", "456", "0924", "23456789", "CreditCard" },
                        { "Card 2", "3", "789", "0328", "34567890", "DebitCard" },
        };

        private String[][] sessions = {
                        { "1", "2024-06-01 08:00:00", "1", "2024-06-01 10:00:00" },
                        { "1", "2024-07-01 10:00:00", "2", "2024-07-01 12:00:00" },
                        { "2", "2024-06-01 08:00:00", "3", "2024-06-01 10:00:00" },
                        { "2", "2024-07-01 10:00:00", "4", "2024-07-01 12:00:00" },
                        { "3", "2024-07-01 10:00:00", "5", "2024-07-01 12:00:00" },
                        { "3", "2024-08-01 12:00:00", "4", "2024-08-01 14:00:00" },
                        { "4", "2024-07-01 10:00:00", "3", "2024-07-01 12:00:00" },
                        { "4", "2024-09-01 14:00:00", "2", "2024-09-01 16:00:00" },
                        { "5", "2024-09-01 14:00:00", "1", "2024-09-01 16:00:00" },
                        { "5", "2024-10-01 16:00:00", "2", "2024-10-01 18:00:00" },
        };

        public void run(ApplicationArguments args) {
                if (!Boolean.parseBoolean(loadData)) {
                        return;
                }

                for (Account a : accounts) {
                        account.save(a);
                        customer.save(new Customer(false, a));
                }

                for (String[] p : paypals) {
                        paypal.save(new PayPal(p[0], customer.findById(Integer.valueOf(p[1])).orElseThrow(), p[2],
                                        p[3]));
                }

                for (String[] c : cards) {
                        card.save(new Card(c[0], customer.findById(Integer.parseInt(c[1])).orElseThrow(),
                                        PaymentCardType.valueOf(c[5]), Integer.parseInt(c[4]), Integer.parseInt(c[4]),
                                        Integer.parseInt(c[2])));
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                                "yyyy-MM-dd HH:mm:ss");
                Location l = location
                                .save(new Location("Sports Center", 200, Time.valueOf("09:20:00"),
                                                Time.valueOf("21:20:00")));

                instructor.save(new Instructor(account.findById(1).orElseThrow())); // tiffany
                instructor.save(new Instructor(account.findById(3).orElseThrow()));
                instructor.save(new Instructor(account.findById(4).orElseThrow()));

                owner.save(new Owner(accounts[5]));

                course.save(new Course("Basketball Fundamentals",
                                "This course covers the fundamentals of basketball including dribbling, shooting, and defense.",
                                CourseStatus.Approved, true, 79, 5));
                course.save(new Course("Soccer Basics",
                                "Learn the basics of soccer including passing, shooting, and tactics.",
                                CourseStatus.Pending, false, 59, 2));
                course.save(new Course("Yoga for Beginners",
                                "This course focuses on improving flexibility, strength, and balance through yoga practice.",
                                CourseStatus.Approved, true, 99, 4));
                course.save(new Course("Introduction to Martial Arts",
                                "Explore the techniques of martial arts including kicks, punches, and blocks.",
                                CourseStatus.Refused,
                                true, 69, 200));
                Course basketballCourse = course.save(new Course("Tennis Fundamentals",
                                "This course introduces students to the principles of tennis including serving, volleying, and strategy.",
                                CourseStatus.Pending, true, 89, 5));

                Session so;
                for (String[] s : sessions) {
                        so = session.save(new Session(LocalDateTime.parse(s[1], formatter),
                                        LocalDateTime.parse(s[3], formatter),
                                        course.findById(Integer.parseInt(s[2])).orElseThrow(), l));
                        if (s[2].equals("1")){ // register + assign for basket only
                            //reg.save(new Registration(customer.findById().orElseThrow(), so));
                            ia.save(new InstructorAssignment(instructor.findById(accounts[2].getId()).orElseThrow(), so)); // michael is assigned
                        }
                        if (s[2].equals("3")){ // assign TIFF for YOGA only
                            //reg.save(new Registration(customer.findById().orElseThrow(), so));
                            ia.save(new InstructorAssignment(instructor.findById(accounts[0].getId()).orElseThrow(), so)); // TIFF is assigned
                        }
                }            
        }
}
