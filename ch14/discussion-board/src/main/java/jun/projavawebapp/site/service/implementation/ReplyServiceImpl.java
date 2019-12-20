package jun.projavawebapp.site.service.implementation;

import jun.projavawebapp.site.dao.ReplyRepository;
import jun.projavawebapp.site.dao.entity.Discussion;
import jun.projavawebapp.site.dao.entity.Reply;
import jun.projavawebapp.site.service.DiscussionService;
import jun.projavawebapp.site.service.ReplyService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {

    private DiscussionService discussionService;
    private ReplyRepository replyRepository;

    @Override
    public List<Reply> getReplies(long discussionId) {
        List<Reply> replies = replyRepository.getReplies(discussionId);
        replies.sort(Comparator.comparing(Reply::getId));
        return replies;
    }

    @Override
    public Reply getReply(long replyId) {
        return replyRepository.getReply(replyId);
    }

    @Override
    public void saveReply(Reply reply) {

        Discussion discussion = discussionService
                .getDiscussion(reply.getDiscussionId());

        if (reply.getId() == 0) {
            discussion.getSubscribedUsers().add(reply.getUser());
            reply.setCreated(Instant.now());
            replyRepository.add(reply);
            discussionService.saveDiscussion(discussion);
        } else {
            replyRepository.update(reply);
        }
    }

    @Inject
    public void setDiscussionService(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    @Inject
    public void setReplyRepository(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }
}
