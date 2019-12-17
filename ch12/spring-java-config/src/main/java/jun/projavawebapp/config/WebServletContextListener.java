package jun.projavawebapp.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class WebServletContextListener implements ServletContextListener {

    private final static Logger logger = LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("sce:" + sce.toString());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("sce:" + sce.toString());
    }
}
