package jun.projavawebapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"jun.projavawebapp.site"},
        includeFilters = @ComponentScan.Filter(Controller.class))
public class ServletContextConfiguration implements WebMvcConfigurer {

    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        // ServletContextConfiguration enables Spring MVC with @EnableWebMvc
        // and configures the DefaultServlet with DefaultServletHandlerConfigurer's
        // enable() method.
        configurer.enable();
    }
}
