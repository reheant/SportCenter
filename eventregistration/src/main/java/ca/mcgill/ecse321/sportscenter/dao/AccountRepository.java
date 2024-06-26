package ca.mcgill.ecse321.sportscenter.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportscenter.model.Account;

public interface AccountRepository extends CrudRepository<Account, Integer> {
    Account findAccountByEmail(String email);

    List<Account> findAll();
}
