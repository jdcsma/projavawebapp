package jun.projavawebapp.site;

import jun.projavawebapp.site.entity.User;
import jun.projavawebapp.site.entity.UserForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class UserManagementController {

    private final Map<Long, User> userDatabase = new ConcurrentHashMap<>();
    private volatile long userIdSequence = 1L;

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

    @RequestMapping(value = "user/list", method = RequestMethod.GET)
    public String displayUsers(Map<String, Object> model) {
        model.put("userList", this.userDatabase.values());
        return "user/list";
    }

    @RequestMapping(value = "user/add", method = RequestMethod.GET)
    public String createUser(Map<String, Object> model) {
        model.put("userForm", new UserForm());
        return "user/add";
    }

    @RequestMapping(value = "user/add", method = RequestMethod.POST)
    public View createUser(UserForm form) {
        User user = new User();
        user.setUserId(this.getNextUserId());
        user.setUsername(form.getUsername());
        user.setName(form.getName());
        this.userDatabase.put(user.getUserId(), user);

        return new RedirectView("/user/list", true, false);
    }

    @RequestMapping(value = "user/edit/{userId}", method = RequestMethod.GET)
    public String editUser(Map<String, Object> model,
                           @PathVariable("userId") long userId) {
        User user = this.userDatabase.get(userId);
        UserForm form = new UserForm();
        form.setUsername(user.getUsername());
        form.setName(user.getName());
        model.put("userForm", form);

        return "user/edit";
    }

    @RequestMapping(value = "user/edit/{userId}", method = RequestMethod.POST)
    public View editUser(UserForm form, @PathVariable("userId") long userId) {
        User user = this.userDatabase.get(userId);
        user.setUsername(form.getUsername());
        user.setName(form.getName());

        return new RedirectView("/user/list", true, false);
    }

    private synchronized long getNextUserId() {
        return this.userIdSequence++;
    }
}
