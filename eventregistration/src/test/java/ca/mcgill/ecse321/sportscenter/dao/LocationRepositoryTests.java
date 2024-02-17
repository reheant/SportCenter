package ca.mcgill.ecse321.sportscenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.sql.Time;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.sportscenter.model.Location;



@SpringBootTest
public class LocationRepositoryTests {

    @Autowired
    private LocationRepository locationRepository;


    @AfterEach
    public void clearDatabase() {
        locationRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadCard() {

        // Create location
        String name = "Ana";
        int capacity = 44;
        Time openingTime = Time.valueOf("08:00:00");
        Time closingTime = Time.valueOf("12:00:00");
        Location location = new Location(name, capacity, openingTime, closingTime);
        // Load Location
        locationRepository.save(location);

        Location dbLocation = locationRepository.findById(location.getId()).orElse(null);

        // Assert that location is not null and has correct attributes.
        assertNotNull(dbLocation);
        assertEquals(name, dbLocation.getName());
        assertEquals(capacity, dbLocation.getCapacity());
        assertEquals(openingTime, dbLocation.getOpeningTime());
        assertEquals(closingTime, dbLocation.getClosingTime());

    }
}
