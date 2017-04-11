package core.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import core.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "core")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	@Qualifier(value = CustomUserDetailsService.BEAN_NAME)
	private UserDetailsService service;
	
	@Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service);
    }
	
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			
			.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
			.antMatchers("/resources/**").permitAll()
				
			.antMatchers("/**").permitAll()
			.anyRequest().authenticated()
			.and().httpBasic();
				
	}

}
