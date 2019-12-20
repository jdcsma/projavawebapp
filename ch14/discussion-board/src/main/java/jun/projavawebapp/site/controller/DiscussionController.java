package jun.projavawebapp.site.controller;

import jun.projavawebapp.site.controller.form.ReplyForm;
import jun.projavawebapp.site.dao.entity.Discussion;
import jun.projavawebapp.site.dao.entity.Reply;
import jun.projavawebapp.site.service.DiscussionService;
import jun.projavawebapp.site.service.ReplyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("discussion/{discussionId:\\d+}")
public class DiscussionController {

    private DiscussionService discussionService;
    private ReplyService replyService;

    @RequestMapping(value = {"", "*"}, method = RequestMethod.GET)
    public String viewDiscussion(
            Map<String, Object> model,
            @PathVariable("discussionId") long id) {

        Discussion discussion = discussionService.getDiscussion(id);

        if (discussion != null) {
            List<Reply> replies = replyService.getReplies(id);
            model.put("discussion", discussion);
            model.put("replies", replies);
            model.put("replyForm", new ReplyForm());
            return "discussion/view";
        }

        return "discussion/errorNoDiscussion";
    }

    @RequestMapping(value = "reply", method = RequestMethod.POST)
    public ModelAndView replyDiscussion(
            ReplyForm form, @PathVariable("discussionId") long id) {

        Discussion discussion = discussionService.getDiscussion(id);

        if (discussion != null) {

            Reply reply = new Reply();
            reply.setDiscussionId(id);
            reply.setUser(form.getUser());
            reply.setMessage(form.getMessage());
            replyService.saveReply(reply);

            return new ModelAndView(new RedirectView(
                    "/discussion/" + discussion.getId() + "/" + discussion.getUriSafeSubject(),
                    true, false));
        }

        return new ModelAndView("discussion/errorNoDiscussion");
    }

    @Inject
    public void setDiscussionService(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    @Inject
    public void setReplyService(ReplyService replyService) {
        this.replyService = replyService;
    }
}
