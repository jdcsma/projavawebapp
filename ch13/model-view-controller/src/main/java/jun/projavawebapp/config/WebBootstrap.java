package jun.projavawebapp.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebBootstrap implements WebApplicationInitializer {

    private final static Logger logger = LogManager.getLogger();

    @Override
    public void onStartup(ServletContext container)
            throws ServletException {

        /**
         * <servlet-mapping>
         *     <servlet-name>default</servlet-name>
         *     <url-pattern>/static/*</url-pattern>
         *     <url-pattern>/</url-pattern>
         * </servlet-mapping>
         */
        // DefaultServlet is a default resource-serving servlet for most web applications,
        // used to serve static resources such as HTML pages and images.
        container.getServletRegistration("default")
                .addMapping("/static/*");

        /**
         * <context-param>
         *     <param-name>contextConfigLocation</param-name>
         *     <param-value>WEB-INF/rootContext.xml</param-value>
         * </context-param>
         */
        AnnotationConfigWebApplicationContext rootContext =
                new AnnotationConfigWebApplicationContext();
        rootContext.register(RootContextConfiguration.class);

        // Register Root context into global servlet.
        container.addListener(new ContextLoaderListener(rootContext));

        // The rootContext's display name is "Root WebApplicationContext".

        /**
         * <servlet>
         *     <servlet-name>springDispatcher</servlet-name>
         *     <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
         *     <init-param>
         *         <param-name>contextConfigLocation</param-name>
         *         <param-value>/WEB-INF/servletContext.xml</param-value>
         *     </init-param>
         *     <load-on-startup>1</load-on-startup>
         * </servlet>
         */
        AnnotationConfigWebApplicationContext servletContext =
                new AnnotationConfigWebApplicationContext();
        servletContext.register(ServletContextConfiguration.class);

        // Register servlet context into customized servlet.
        ServletRegistration.Dynamic dispatcher = container.addServlet(
                "springDispatcher", new DispatcherServlet(servletContext));
        dispatcher.setLoadOnStartup(1);

        // The servletContext's display name is "WebApplicationContext for namespace 'springDispatcher-servlet'".
        // See stack as follow:
        //    setDisplayName:274, AbstractApplicationContext (org.springframework.context.support)
        //    setNamespace:135, AbstractRefreshableWebApplicationContext (org.springframework.web.context.support)
        //    configureAndRefreshWebApplicationContext:689, FrameworkServlet (org.springframework.web.servlet)
        //    initWebApplicationContext:578, FrameworkServlet (org.springframework.web.servlet)
        //    initServletBean:530, FrameworkServlet (org.springframework.web.servlet)
        //    init:170, HttpServletBean (org.springframework.web.servlet)

        /**
         * <servlet-mapping>
         *     <servlet-name>springDispatcher</servlet-name>
         *     <url-pattern>/</url-pattern>
         * </servlet-mapping>
         */
        // We register the Spring DispatcherServlet to the / path.
        // This replaces the DefaultServlet; therefore we have to
        // register a default servlet handler in the ServletContextConfiguration
        // configuration file (use DefaultServletHandlerConfigurer.enable method in
        // WebMvcConfigurer.configureDefaultServletHandling method).
        dispatcher.addMapping("/");
    }
}
