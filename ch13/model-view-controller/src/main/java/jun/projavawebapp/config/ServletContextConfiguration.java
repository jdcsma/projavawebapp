package jun.projavawebapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.servlet.RequestToViewNameTranslator;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.inject.Inject;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"jun.projavawebapp.site"},
        includeFilters = {@ComponentScan.Filter(Controller.class)})
public class ServletContextConfiguration implements WebMvcConfigurer {

    private final static Logger logger = LogManager.getLogger();

    private Marshaller marshaller;
    private Unmarshaller unmarshaller;
    private ObjectMapper mapper;

    @Bean
    public ViewResolver viewResolver() {
        logger.traceEntry();
        InternalResourceViewResolver resolver =
                new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/jsp/view/");
        resolver.setSuffix(".jsp");
        return logger.traceExit(resolver);
    }

    @Bean
    public RequestToViewNameTranslator viewNameTranslator() {
        return logger.traceExit(logger.traceEntry(),
                new DefaultRequestToViewNameTranslator());
    }

    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        // ServletContextConfiguration enables Spring MVC with @EnableWebMvc
        // and configures the DefaultServlet with DefaultServletHandlerConfigurer's
        // enable() method.
        configurer.enable();
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
//        registry.jsp("WEB-INF/jsp/view/", ".jsp")
//                .viewClass(JstlView.class);
        registry.viewResolver(viewResolver());
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(true)
                .favorParameter(false)
                .parameterName("mediaType")
                .ignoreAcceptHeader(false)
                .useJaf(false)
                .defaultContentType(MediaType.APPLICATION_XML)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("json", MediaType.APPLICATION_JSON);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        Assert.notNull(marshaller, "The marshaller is null.");
        Assert.notNull(unmarshaller, "This unmarshaller is null.");
        Assert.notNull(mapper, "This mapper is null.");

        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new StringHttpMessageConverter());
        converters.add(new FormHttpMessageConverter());
        converters.add(new SourceHttpMessageConverter<>());

        MarshallingHttpMessageConverter xmlConverter =
                new MarshallingHttpMessageConverter();
        xmlConverter.setSupportedMediaTypes(Arrays.asList(
                new MediaType("application", "xml"),
                new MediaType("text", "xml")
        ));
        xmlConverter.setMarshaller(marshaller);
        xmlConverter.setUnmarshaller(unmarshaller);
        converters.add(xmlConverter);

        MappingJackson2HttpMessageConverter jsonConverter =
                new MappingJackson2HttpMessageConverter();
        jsonConverter.setSupportedMediaTypes(Arrays.asList(
                new MediaType("application", "json"),
                new MediaType("text", "json")
        ));
        jsonConverter.setObjectMapper(mapper);
        converters.add(jsonConverter);
    }

    @Inject
    public void setMarshaller(Marshaller marshaller) {
        logger.traceEntry("{}", marshaller);
        this.marshaller = marshaller;
        logger.traceExit();
    }

    @Inject
    public void setUnmarshaller(Unmarshaller unmarshaller) {
        logger.traceEntry("{}", unmarshaller);
        this.unmarshaller = unmarshaller;
        logger.traceExit();
    }

    @Inject
    public void setMapper(ObjectMapper mapper) {
        logger.traceEntry("{}", mapper);
        this.mapper = mapper;
        logger.traceExit();
    }
}
