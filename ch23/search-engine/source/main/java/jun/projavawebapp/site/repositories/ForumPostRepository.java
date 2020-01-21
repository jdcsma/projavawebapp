package jun.projavawebapp.site.repositories;

import jun.projavawebapp.site.entities.ForumPost;
import org.springframework.data.repository.CrudRepository;

public interface ForumPostRepository extends
        CrudRepository<ForumPost, Long>,
        SearchableRepository<ForumPost> {
}
