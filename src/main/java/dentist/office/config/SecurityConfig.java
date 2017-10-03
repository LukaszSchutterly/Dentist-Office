package dentist.office.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll();
        http.httpBasic();
        http.authorizeRequests().antMatchers("/doctor").hasRole("DOCTOR");
        http.authorizeRequests().antMatchers("/visits/**").hasRole("DOCTOR");
        http.authorizeRequests().antMatchers("/patients/**").hasRole("DOCTOR");
        http.authorizeRequests().antMatchers("/daySchemes").hasRole("DOCTOR");
        http.authorizeRequests().antMatchers("/daySchemes/notConfirmedVisitTimes").hasRole("DOCTOR");
        http.authorizeRequests().antMatchers("/templates/doctor/**").hasRole("DOCTOR");
        http.authorizeRequests().antMatchers("/modules/doctor/visits/**").hasRole("DOCTOR");
        http.authorizeRequests().antMatchers("/modules/doctor/patients/**").hasRole("DOCTOR");
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("doctor").password("pass").roles("DOCTOR");
    }

}
