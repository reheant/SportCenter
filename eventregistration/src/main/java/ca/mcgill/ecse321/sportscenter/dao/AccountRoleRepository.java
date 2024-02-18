package ca.mcgill.ecse321.sportscenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportscenter.model.AccountRole;

/**
 * AccountRoleRepository
 */
public interface AccountRoleRepository extends CrudRepository<AccountRole, Integer> {
}
