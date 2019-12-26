package jun.projavawebapp.site;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener,
        HttpSessionIdListener, ServletContextListener {

    private static final Logger log = LogManager.getLogger();

    @Inject
    SessionRegistry sessionRegistry;

    @Override
    public void sessionCreated(HttpSessionEvent e) {
        log.debug("Session " + e.getSession().getId() + " created.");
        this.sessionRegistry.addSession(e.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent e) {
        log.debug("Session " + e.getSession().getId() + " destroyed.");
        this.sessionRegistry.removeSession(e.getSession());
    }

    @Override
    public void sessionIdChanged(HttpSessionEvent e, String oldSessionId) {
        log.debug("Session ID " + oldSessionId + " changed to " + e.getSession().getId());
        this.sessionRegistry.updateSessionId(e.getSession(), oldSessionId);
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {

        // 1) 获取 root WebApplicationContext (通常是由 ContextLoaderListener 加载)
        WebApplicationContext context =
                WebApplicationContextUtils.getRequiredWebApplicationContext(
                        event.getServletContext());

        // 2) 获取具有自动装配能力 bean 工厂。
        AutowireCapableBeanFactory factory =
                context.getAutowireCapableBeanFactory();

        // 3) 通过 name 或 type 自动装配指定 bean 对象的 bean 属性（被 @Inject 或 @Autowired 注解的对象）。
        factory.autowireBeanProperties(this,
                AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);

        // 4) 初始化给定的原始 bean 对象，应用工厂回调，
        // 例如 BeanNameAware.setBeanName 和 BeanFactoryAware.setBeanFactory，
        // 还应用所有 bean 后处理器（包括可能包装给定原始 bean 的处理器）。
        factory.initializeBean(this, "sessionListener");
        log.info("Session listener initialized in Spring application context.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
    }
}
