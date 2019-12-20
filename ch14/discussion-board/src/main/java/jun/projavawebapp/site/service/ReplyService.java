package jun.projavawebapp.site.service;

import jun.projavawebapp.site.dao.entity.Reply;

import java.util.List;

public interface ReplyService {

    List<Reply> getReplies(long discussionId);

    Reply getReply(long replyId);

    void saveReply(Reply reply);
}
