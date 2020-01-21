package jun.projavawebapp.site;

import jun.projavawebapp.site.entities.ForumPost;
import jun.projavawebapp.site.entities.User;
import jun.projavawebapp.site.repositories.SearchResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MainService {

    User getUser(String username);

    Page<SearchResult<ForumPost>> search(String query, Pageable pageable);

    void save(ForumPost forumPost);
}
