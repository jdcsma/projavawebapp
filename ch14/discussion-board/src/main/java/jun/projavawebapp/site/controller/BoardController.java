package jun.projavawebapp.site.controller;

import jun.projavawebapp.site.controller.form.DiscussionForm;
import jun.projavawebapp.site.dao.entity.Discussion;
import jun.projavawebapp.site.service.DiscussionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("discussion")
public class BoardController {

    private DiscussionService discussionService;

    @RequestMapping(value = {"", "list"})
    public String listDiscussions(Map<String, Object> model) {
        List<Discussion> discussions = discussionService.getDiscussions();
        model.put("discussions", discussions);
        return "discussion/list";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createDiscussion(Map<String, Object> model) {
        model.put("discussionForm", new DiscussionForm());
        return "discussion/create";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public View createDiscussion(DiscussionForm form) {

        Discussion discussion = new Discussion();
        discussion.setUser(form.getUser());
        discussion.setSubject(form.getSubject());
        discussion.setMessage(form.getMessage());

        discussionService.saveDiscussion(discussion);

        return new RedirectView(
                "/discussion/" + discussion.getId() + "/" + discussion.getUriSafeSubject(),
                true, false);
    }

    @Inject
    public void setDiscussionService(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }
}
