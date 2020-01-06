package jun.projavawebapp.site;

import jun.projavawebapp.config.annotation.WebController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.rmi.runtime.Log;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@WebController
public class HomeController implements InitializingBean {

    private final static Logger log = LogManager.getLogger();

    @Inject
    ApplicationEventPublisher publisher;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("publisher:" + publisher);
    }

    @RequestMapping("")
    public String login(HttpServletRequest request) {
        this.publisher.publishEvent(new LoginEvent(request.getRemoteAddr()));
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        this.publisher.publishEvent(new LogoutEvent(request.getRemoteAddr()));
        return "logout";
    }
}
