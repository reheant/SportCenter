package ca.mcgill.ecse321.sportscenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportscenter.model.Location;

public interface LocationRepository extends CrudRepository<Location, Integer> {
    Location findLocationByName(String name);
}
