package jun.projavawebapp.site.service;

import jun.projavawebapp.site.dao.entity.Discussion;

import java.util.List;

public interface DiscussionService {

    List<Discussion> getDiscussions();

    Discussion getDiscussion(long discussionId);

    void saveDiscussion(Discussion discussion);
}
