package jun.projavawebapp.site.dao.implementation;

import jun.projavawebapp.site.dao.ReplyRepository;
import jun.projavawebapp.site.dao.entity.Reply;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryReplyRepository implements ReplyRepository {

    private final Map<Long, Reply> replyDatabase = new ConcurrentHashMap<>();
    private volatile long replyIdSequence = 1L;

    @Override
    public List<Reply> getReplies(long discussionId) {
        return Arrays.asList(replyDatabase.values().stream()
                .filter(x -> x.getDiscussionId() == discussionId)
                .toArray(Reply[]::new));
    }

    @Override
    public Reply getReply(long replyId) {
        return replyDatabase.get(replyId);
    }

    @Override
    public void add(Reply reply) {
        reply.setId(getNextReplyId());
        replyDatabase.put(reply.getId(), reply);
     }

    @Override
    public void update(Reply reply) {
        replyDatabase.put(reply.getId(), reply);
    }

    private synchronized long getNextReplyId() {
        return replyIdSequence++;
    }
}
