package ca.mcgill.ecse321.sportscenter.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportscenter.model.Owner;

public interface OwnerRepository extends CrudRepository<Owner, Integer> {
    
    List<Owner> findAll();

}
