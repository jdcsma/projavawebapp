package jun.projavawebapp.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Controller;
import sun.rmi.runtime.Log;

import java.util.concurrent.Executor;

/**
 * 该类用于配置全局共享的 bean
 */
@Configuration
@EnableAsync(proxyTargetClass = true)
@EnableScheduling
@ComponentScan(basePackages = {"jun.projavawebapp.site"},
        /**
         * 将同时包含默认包含过滤器和排除过滤器。
        */
        excludeFilters = {@ComponentScan.Filter(Controller.class)})
public class RootContextConfiguration
        implements AsyncConfigurer, SchedulingConfigurer {

    private final static Logger logger = LogManager.getLogger();
    private final static Logger schedulingLogger =
            LogManager.getLogger(logger.getName() + ".[scheduling]");

    // 实例化 XML 转换器实现。
    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        logger.traceEntry();
        /**
         * Implementation of the GenericMarshaller interface for JAXB 2.2.
         *
         * The typical usage will be to set either the "contextPath" or the "classesToBeBound"
         * property on this bean, possibly customize the marshaller and unmarshaller by setting
         * properties, schemas, adapters, and listeners, and to refer to it.
         */
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan(new String[]{"jun.projavawebapp.site.entity"});
        return logger.traceExit(marshaller);
    }

    // 实例化 JSON 转换器实现。
    @Bean
    public ObjectMapper objectMapper() {
        logger.traceEntry();
        /**
         * ObjectMapper provides functionality for reading and writing JSON,
         * either to and from basic POJOs (Plain Old Java Objects), or to and
         * from a general-purpose JSON Tree Model (JsonNode), as well as related
         * functionality for performing conversions. It is also highly customizable
         * to work both with different styles of JSON content, and to support more
         * advanced Object concepts such as polymorphism and Object identity.
         *
         * ObjectMapper also acts as a factory for more advanced ObjectReader and
         * ObjectWriter classes. Mapper (and ObjectReaders, ObjectWriters it constructs)
         * will use instances of JsonParser and JsonGenerator for implementing actual
         * reading/writing of JSON. Note that although most read and write methods are
         * exposed through this class, some of the functionality is only exposed via
         * ObjectReader and ObjectWriter: specifically, reading/writing of longer
         * sequences of values is only available through ObjectReader.readValues(InputStream)
         * and ObjectWriter.writeValues(OutputStream).
         */
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
        return logger.traceExit(mapper);
    }

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        logger.info("Setting up thread pool task scheduler with 20 threads.");

        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(20);
        scheduler.setThreadNamePrefix("task-");
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        scheduler.setErrorHandler(t -> schedulingLogger.error(
                "Unknown error occurred while executing task.", t
        ));
        scheduler.setRejectedExecutionHandler((r, e) -> schedulingLogger.error(
                "Execution of task {} was rejected for unknown reasons.", r
        ));

        return scheduler;
    }

    @Override
    public Executor getAsyncExecutor() {
        Executor executor = this.taskScheduler();
        logger.info("Configuring asynchronous method executor {}.", executor);
        return executor;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        TaskScheduler scheduler = this.taskScheduler();
        logger.info("Configuring scheduled method executor {}.", scheduler);
        taskRegistrar.setTaskScheduler(scheduler);
    }
}
