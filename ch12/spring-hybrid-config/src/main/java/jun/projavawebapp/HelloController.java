package jun.projavawebapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    private final static Logger logger = LogManager.getLogger();

    private GreetingService greetingService;
    private ApplicationContext context;

    @ResponseBody
    @RequestMapping("/")
    public String helloWorld() {
        logger.traceEntry();
        final String retValue = "Hello, World!";
        return logger.traceExit(retValue);
    }

    @ResponseBody
    @RequestMapping(value = "/", params = {"name"})
    public String helloName(@RequestParam("name") String name) {
        logger.traceEntry("{}ß", name);
        String retValue = greetingService.getGreeting(name);
        return logger.traceExit(retValue);
    }

    @Autowired
    public void setGreetingService(GreetingService greetingService) {
        logger.traceEntry("{}", greetingService);
        this.greetingService = greetingService;
        logger.traceExit();
    }
}
