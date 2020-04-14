package jun.projavawebapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder builder)
            throws Exception {
        builder
                .inMemoryAuthentication()
                .withUser("John")
                .password("password")
                .authorities("USER")
                .and()
                .withUser("Margaret")
                .password("green")
                .authorities("USER", "ADMIN");
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/resource/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()

                .antMatchers("/signup", "/about", "/policies")
                .permitAll()

                .antMatchers("/secure/**")
                .hasAuthority("USER")

                .antMatchers("/admin/**")
                .hasAuthority("ADMIN")

                .anyRequest()
                .authenticated()

                .and()

                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .defaultSuccessUrl("/secure/")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()

                .and()

                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?loggedOut")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()

                .and()

                .sessionManagement()
                .sessionFixation()
                .changeSessionId()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)

                .and()

                .and()

                .csrf()
                .disable();
    }
}
