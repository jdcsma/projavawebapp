package jun.projavawebapp.site.dao.implementation;

import jun.projavawebapp.site.dao.DiscussionRepository;
import jun.projavawebapp.site.dao.entity.Discussion;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryDiscussionRepository implements DiscussionRepository {

    private final Map<Long, Discussion> database = new ConcurrentHashMap<>();
    private volatile long discussionIdSequence = 1L;

    @Override
    public List<Discussion> getDiscussions() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Discussion get(long discussionId) {
        return database.get(discussionId);
    }

    @Override
    public void add(Discussion discussion) {
        discussion.setId(getNextDiscussionId());
        database.put(discussion.getId(), discussion);
    }

    @Override
    public void update(Discussion discussion) {
        database.put(discussion.getId(), discussion);
    }

    private synchronized long getNextDiscussionId() {
        return discussionIdSequence++;
    }
}
