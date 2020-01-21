package jun.projavawebapp.site.repositories;

import jun.projavawebapp.site.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends
        CrudRepository<User, Long> {
    User getByUsername(String username);
}
