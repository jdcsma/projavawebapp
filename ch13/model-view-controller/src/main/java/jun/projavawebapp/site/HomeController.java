package jun.projavawebapp.site;

import jun.projavawebapp.site.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Map;

@Controller
public class HomeController {

    private final static Logger logger = LogManager.getLogger();

    public HomeController() {
        logger.traceExit(logger.traceEntry("constructor()"));
    }

    /**
     * Spring 对控制器方法返回值的处理：
     *
     * 1）返回 View 或 ModelAndView（将 View 的实现传入到 ModelAndView 构造器中）的实现时，
     * Spring 将直接使用该 View，并且不需要额外的逻辑用于判断如何向客户端展示模型。
     *
     * 2）返回 String 视图名称或使用 String 视图名称构造的 ModelAndView 时，Spring 必须使用已配置的
     * org.springframework.web.servlet.ViewResolver 将视图名称解析成一个真正的视图（例如：JSP 文件名）。
     *
     * 3）返回 model 或 model attribute 时，Spring 首先必须使用已配置的 RequestToViewNameTranslator
     * 隐式地将请求转换成视图名称，然后使用 ViewResolver 解析已命名的视图。
     *
     * 4）返回 ResponseEntity 或 HttpEntity 时，Spring 将使用内容协商决定将实体展示到那个视图中。
     */

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public View index(Map<String, Object> model, HttpServletRequest request) {
        logger.traceEntry("model:{}", model);
        model.put("homeUrl", "home");

        /**
         * The HttpServletResponse.sendDirect() method can accept relative URLs;
         *
         * The servlet container must convert the relative URL to an absolute URL before sending
         * the response to the client.
         *
         * 1) If the location is relative without a leading '/' the container interprets it as relative
         * to the current request URI.
         *
         * 2) If the location is relative with a leading '/' the container interprets it as relative
         * to the servlet container root (HttpServletRequest.getContextPath()).
         *
         * 3) If the location is relative with two leading '/' the container interprets it as a network-path
         * reference.
         *
         * In this case, the URL and the servlet container root as follows:
         *     request.getRequestURL(): http://localhost:8080/site/
         *     request.getRequestURI(): /site/
         *     request.getContextPath(): /site
         */
//        return new RedirectView("/site/{homeUrl}", true); // case (2
        return logger.traceExit(new RedirectView("{homeUrl}", true)); // case (1
    }

    @RequestMapping(value = "/home", method = {RequestMethod.GET})
    public String home(Map<String, Object> model) {
        logger.traceEntry("model:{}", model);
        model.put("text", "This is a model attribute.");
        model.put("date", Instant.now());
        return logger.traceExit("/home");
    }

    @RequestMapping(value = "/user", method = {RequestMethod.GET})
    @ModelAttribute("currentUser")
    public User user() {
        logger.traceEntry();
        User user = new User();
        user.setUserId(1L);
        user.setUsername("adam");
        user.setRole("admin");
        return logger.traceExit(user);
    }

    @RequestMapping(value = "/user/{userId}", method = {RequestMethod.GET})
    @ResponseBody
    public User user(@PathVariable("userId") long userId) {
        logger.traceEntry("userId:{}", userId);
        User user = new User();
        user.setUserId(userId);
        user.setUsername("john");
        user.setRole("user");
        return logger.traceExit(user);
    }

//    @RequestMapping(value = "/user/{userId}", method = {RequestMethod.GET})
//    public ResponseEntity<User> user(@PathVariable("userId") long userId) {
//        logger.traceEntry("userId:{}", userId);
//        User user = new User();
//        user.setUserId(userId);
//        user.setUsername("john");
//        user.setRole("user");
//        return logger.traceExit(ResponseEntity.ok(user));
//    }
}
