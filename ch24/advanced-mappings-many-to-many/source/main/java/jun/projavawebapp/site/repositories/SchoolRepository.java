package jun.projavawebapp.site.repositories;

import jun.projavawebapp.site.entities.School;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SchoolRepository extends
        CrudRepository<School, Long> {

    Optional<School> findByName(String name);
}
