package jun.projavawebapp.site.repositories;

import jun.projavawebapp.site.entities.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends
        CrudRepository<Person, Long> {
}
