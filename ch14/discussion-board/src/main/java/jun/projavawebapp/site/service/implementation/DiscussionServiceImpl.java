package jun.projavawebapp.site.service.implementation;

import jun.projavawebapp.site.dao.DiscussionRepository;
import jun.projavawebapp.site.dao.entity.Discussion;
import jun.projavawebapp.site.service.DiscussionService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.text.Normalizer;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@Service
public class DiscussionServiceImpl implements DiscussionService {

    private DiscussionRepository discussionRepository;

    @Override
    public List<Discussion> getDiscussions() {
        List<Discussion> discussions = discussionRepository.getDiscussions();
        discussions.sort(Comparator.comparing(Discussion::getLastUpdated));
        return discussions;
    }

    @Override
    public Discussion getDiscussion(long discussionId) {
        return discussionRepository.get(discussionId);
    }

    @Override
    public void saveDiscussion(Discussion discussion) {
        String subject = discussion.getSubject();
        subject = Normalizer.normalize(subject.toLowerCase(), Normalizer.Form.NFD)
                .replaceAll("\\p{InCOMBINING_DIACRITICAL_MARKS}+", "")
                .replaceAll("[^\\p{Alnum}]+", "-")
                .replace("--", "-").replace("--", "-")
                .replaceAll("[^a-z0-9]+$", "")
                .replaceAll("^[^a-z0-9]+", "");
        discussion.setUriSafeSubject(subject);

        Instant now = Instant.now();
        discussion.setLastUpdated(now);

        if (discussion.getId() == 0) {
            discussion.setCreated(now);
            discussion.getSubscribedUsers().add(discussion.getUser());
            discussionRepository.add(discussion);
        } else {
            discussionRepository.update(discussion);
        }
    }

    @Inject
    public void setDiscussionRepository(DiscussionRepository discussionRepository) {
        this.discussionRepository = discussionRepository;
    }
}
