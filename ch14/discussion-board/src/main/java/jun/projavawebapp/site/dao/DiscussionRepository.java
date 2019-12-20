package jun.projavawebapp.site.dao;

import jun.projavawebapp.site.dao.entity.Discussion;

import java.util.List;

public interface DiscussionRepository {

    List<Discussion> getDiscussions();

    Discussion get(long discussionId);

    void add(Discussion discussion);

    void update(Discussion discussion);
}
