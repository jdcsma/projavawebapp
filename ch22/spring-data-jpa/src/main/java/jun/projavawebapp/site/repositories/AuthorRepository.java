package jun.projavawebapp.site.repositories;

import jun.projavawebapp.site.entities.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {

}
