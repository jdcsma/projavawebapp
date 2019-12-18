package jun.projavawebapp.site;

import jun.projavawebapp.site.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.time.Instant;
import java.util.Map;

@Controller
public class HomeController {

    private final static Logger logger = LogManager.getLogger();

    public HomeController() {
        logger.traceExit(logger.traceEntry("constructor()"));
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public View index(Map<String, Object> model) {
        model.put("homeUrl", "home");
        return new RedirectView("/{homeUrl}", true);
    }

    @RequestMapping(value = "/home", method = {RequestMethod.GET})
    public String home(Map<String, Object> model) {
        model.put("text", "This is a model attribute.");
        model.put("date", Instant.now());
        return "/home";
    }

    @RequestMapping(value = "/user", method = {RequestMethod.GET})
    @ModelAttribute("currentUser")
    public User user() {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("adam");
        user.setRole("admin");
        return user;
    }

    @RequestMapping(value = "/user/{userId}", method = {RequestMethod.GET})
    @ResponseBody
    public User user(@PathVariable("userId") long userId) {
        User user = new User();
        user.setUserId(userId);
        user.setUsername("john");
        user.setRole("user");
        return user;
    }
}
