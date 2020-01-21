package jun.projavawebapp.site.repositories;

import jun.projavawebapp.site.entities.UserPrincipal;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository
        extends CrudRepository<UserPrincipal, Long> {
    UserPrincipal getByUsername(String username);
}
