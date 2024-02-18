package ca.mcgill.ecse321.sportscenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportscenter.model.Card;

/**
 * CardRepository
 */
public interface CardRepository extends CrudRepository<Card, Integer> {


}
