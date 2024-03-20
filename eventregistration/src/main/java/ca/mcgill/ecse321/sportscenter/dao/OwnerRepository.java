package ca.mcgill.ecse321.sportscenter.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportscenter.model.Owner;

public interface OwnerRepository extends CrudRepository<Owner, Integer> {

    @Query("SELECT c FROM Owner c WHERE c.account.email = :email")
    Owner findOwnerByEmail(String email);
    List<Owner> findAll();
}
