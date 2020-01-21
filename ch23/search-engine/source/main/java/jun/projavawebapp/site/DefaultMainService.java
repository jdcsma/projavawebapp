package jun.projavawebapp.site;

import jun.projavawebapp.site.entities.ForumPost;
import jun.projavawebapp.site.entities.User;
import jun.projavawebapp.site.repositories.ForumPostRepository;
import jun.projavawebapp.site.repositories.SearchResult;
import jun.projavawebapp.site.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
public class DefaultMainService implements MainService {

    @Inject
    UserRepository userRepository;
    @Inject
    ForumPostRepository forumPostRepository;

    @Override
    @Transactional
    public User getUser(String username) {
        return this.userRepository.getByUsername(username);
    }

    @Override
    @Transactional
    public Page<SearchResult<ForumPost>> search(String query, Pageable pageable) {
        return this.forumPostRepository.search(query, pageable);
    }

    @Override
    @Transactional
    public void save(ForumPost forumPost) {
        this.forumPostRepository.save(forumPost);
    }
}
