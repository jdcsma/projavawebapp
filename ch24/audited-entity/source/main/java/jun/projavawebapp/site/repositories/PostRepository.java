package jun.projavawebapp.site.repositories;

import jun.projavawebapp.site.entities.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends
        CrudRepository<Post, Long> {
}
