package jun.projavawebapp.site;

import jun.projavawebapp.site.entities.Author;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultAuthorRepository
        extends GenericJpaRepository<Long, Author>
        implements AuthorRepository {

}
