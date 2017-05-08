package core.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "core")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	protected void configure(HttpSecurity http) throws Exception {
		http
		    .csrf().disable()
		    .authorizeRequests()
		    .antMatchers("/**").permitAll()
		    .anyRequest().authenticated();
				
	}

}
