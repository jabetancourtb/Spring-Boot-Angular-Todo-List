package com.todo.list.api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	private CustomUserDetailsService customUserDetailsService;
    private JwtFilter jwtFilter;
	
	@Autowired 
	public WebSecurityConfiguration(CustomUserDetailsService customUserDetailsService, JwtFilter jwtFilter) {
		this.customUserDetailsService = customUserDetailsService;
		this.jwtFilter = jwtFilter;
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	 http.cors().and().csrf().disable()
         .authorizeRequests().antMatchers("/user/{email}/{password}"
						 , "/user/sign-up"
						 , "/user/get-user-by-verification-code/{verificationCode}"
						 , "/user/update-verification-code/{userId}/{verificationCode}"
						 , "/user/update-state/{userId}/{state}"
						 , "/user-password-recovery/**").permitAll()
         .anyRequest().authenticated()
         .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    	 
    	 http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
     
    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}
    
    @Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
