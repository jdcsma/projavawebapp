package jun.projavawebapp.site.repositories;

import jun.projavawebapp.site.entities.NewsArticle;
import org.springframework.data.repository.CrudRepository;

public interface NewsArticleRepository extends
        CrudRepository<NewsArticle, Long> {
}
