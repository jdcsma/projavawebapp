package jun.projavawebapp.config;

import jun.projavawebapp.site.AuthenticationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;

import javax.inject.Inject;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final Logger log = LogManager.getLogger();

    @Inject
    AuthenticationService authenticationService;

    @Bean
    protected SessionRegistry sessionRegistryImpl() {
        return new SessionRegistryImpl();
    }

    @Bean
    protected ApplicationListener<InteractiveAuthenticationSuccessEvent> testSessionRegister() {
        return e -> log.debug("receive InteractiveAuthenticationSuccessEvent: " + e);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) {
        builder.authenticationProvider(this.authenticationService);
    }

    @Override
    public void configure(WebSecurity security) {
        security.ignoring().antMatchers("/resource/**", "/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/login").failureUrl("/login?loginFailed")
                .defaultSuccessUrl("/ticket/list")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and().logout()
                .logoutUrl("/logout").logoutSuccessUrl("/login?loggedOut")
                .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .permitAll()
                .and().sessionManagement()
                .sessionFixation().changeSessionId()
                .maximumSessions(1).maxSessionsPreventsLogin(true)
                .sessionRegistry(this.sessionRegistryImpl())
                .and().and().csrf()
                .requireCsrfProtectionMatcher((r) -> {
                    String m = r.getMethod();
                    return !r.getServletPath().startsWith("/services/") &&
                            ("POST".equals(m) || "PUT".equals(m) ||
                                    "DELETE".equals(m) || "PATCH".equals(m));
                });
    }
}
