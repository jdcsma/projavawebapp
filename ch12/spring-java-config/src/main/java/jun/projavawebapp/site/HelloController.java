package jun.projavawebapp.site;

import jun.projavawebapp.site.service.GreetingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

@Controller
public class HelloController {

    private final static Logger logger = LogManager.getLogger();

    private GreetingService greetingService;

    public HelloController() {
        logger.traceExit(logger.traceEntry("constructor()"));
    }

    @RequestMapping("/hello")
    @ResponseBody
    public String helloWorld() {
        return "Hello, World!";
    }

    @RequestMapping(value = "/hello", params = {"name"})
    @ResponseBody
    public String helloName(@RequestParam("name") String name) {
        return greetingService.getGreetingText(name);
    }

    @Inject // or @Autowired
    public void setGreetingService(GreetingService greetingService) {
        logger.traceEntry();
        this.greetingService = greetingService;
        logger.traceExit();
    }
}
