package jun.projavawebapp.awarer;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.*;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

@Component
public class SpringBeanAwarer implements
        ApplicationEventPublisherAware, BeanFactoryAware,
        EnvironmentAware, MessageSourceAware, ServletContextAware,
        ServletConfigAware, InitializingBean, DisposableBean {

    private final Logger logger = LogManager.getLogger();

    public SpringBeanAwarer() {
        logger.traceExit(logger.traceEntry("constructor()"));
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        logger.info("setBeanFactory:" + beanFactory.toString());
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        logger.info("setApplicationEventPublisher:" + applicationEventPublisher.toString());
    }

    @Override
    public void setEnvironment(Environment environment) {
        logger.info("setEnvironment:" + environment.toString());
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        logger.info("setMessageSource:" + messageSource.toString());
    }

    @Override
    public void setServletConfig(ServletConfig servletConfig) {
        logger.info("setServletConfig:" + servletConfig.toString());
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        logger.info("setServletContext:" + servletContext.toString());
    }

    //    @PostConstruct
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("afterPropertiesSet:");
    }

    //    @PreDestroy
    @Override
    public void destroy() throws Exception {
        logger.info("destroy:");
    }
}
