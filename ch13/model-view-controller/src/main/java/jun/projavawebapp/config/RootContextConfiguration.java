package jun.projavawebapp.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(basePackages = {"jun.projavawebapp.site"},
        excludeFilters = {@ComponentScan.Filter(Controller.class)})
public class RootContextConfiguration {

    private final static Logger logger = LogManager.getLogger();

    @Bean
    public ObjectMapper objectMapper() {
        logger.traceEntry();
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
        return logger.traceExit(mapper);
    }

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        logger.traceEntry();
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan(new String[]{"jun.projavawebapp.site.entity"});
        return logger.traceExit(marshaller);
    }
}
