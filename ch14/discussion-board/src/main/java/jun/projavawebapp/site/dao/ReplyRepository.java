package jun.projavawebapp.site.dao;

import jun.projavawebapp.site.dao.entity.Reply;

import java.util.List;

public interface ReplyRepository {

    List<Reply> getReplies(long discussionId);

    Reply getReply(long replyId);

    void add(Reply reply);

    void update(Reply reply);
}
