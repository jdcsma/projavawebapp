package jun.projavawebapp.site.service;

import org.springframework.stereotype.Service;

@Service
public class GreetingServiceImpl implements GreetingService {

    @Override
    public String getGreetingText(String name) {
        return "Hello, " + name + "!";
    }
}
