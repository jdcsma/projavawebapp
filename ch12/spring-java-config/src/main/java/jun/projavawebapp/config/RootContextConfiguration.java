package jun.projavawebapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(basePackages = {"jun.projavawebapp.site", "jun.projavawebapp.awarer"},
        excludeFilters = {@ComponentScan.Filter(Controller.class)})
public class RootContextConfiguration {
}
