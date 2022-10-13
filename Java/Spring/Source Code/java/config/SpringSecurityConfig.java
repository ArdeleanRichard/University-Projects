package com.mkyong.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.mkyong.model.User;
import com.mkyong.service.Facade;
import com.mkyong.model.Admin;

@Configuration
// http://docs.spring.io/spring-boot/docs/current/reference/html/howto-security.html
// Switch off the Spring Boot security configuration
//@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    // roles admin allow to access /admin/**
    // roles user allow to access /user/**
    // custom 403 access denied handler
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
	                .antMatchers("/", "/home", "/about", "/getexercises").permitAll()
	                .antMatchers("/admin/**").hasAnyRole("ADMIN")
	                .antMatchers("/user/**").hasAnyRole("USER")
	                .anyRequest().authenticated()
                .and()
                	.formLogin()
	                .loginPage("/login")
	                .permitAll()
                .and()
	                .logout()
	                .permitAll()
                .and()
                	.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }
	
	@Autowired
	public Facade facade;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("u").password("u").roles("USER")
                .and()
                .withUser("a").password("a").roles("ADMIN");
        
    	

    	List<User> users = facade.findAllUsers();
    	List<Admin> admins = facade.findAllAdmins();
    	
    	for(Admin a : admins){
	        auth.inMemoryAuthentication()
	            .withUser(a.getUsername()).password(a.getPassword()).roles("ADMIN");
    	}
        for(User u : users){
			auth.inMemoryAuthentication()
            	.withUser(u.getUsername()).password(u.getPassword()).roles("USER");
		}
    }

    /*
    //Spring Boot configured this already.
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }*/

}
