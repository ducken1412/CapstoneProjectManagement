package com.fpt.config;

import com.fpt.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/", "/login","/_menu", "/logout").permitAll();
		http.authorizeRequests().antMatchers("/pl","/pl/", "/pl/**").access("hasAnyRole('Role_Leader','Role_Member','Role_Head','Role_Lecturer','Role_TrainingDep')");
		http.authorizeRequests().antMatchers("/st","/st/", "/st/**").access("hasAnyRole('Role_Member')");
		http.authorizeRequests().antMatchers("/hd","/hd/", "/hd/**").access("hasRole('Role_Head')");
		http.authorizeRequests().antMatchers("/lt","/lt/", "/lt/**").access("hasRole('Role_Lecturer')");
		http.authorizeRequests().antMatchers("/td","/td/", "/td/**").access("hasRole('Role_TrainingDep')");
		http.authorizeRequests().antMatchers("/ad","/ad/", "/ad/**").access("hasAnyRole('Role_Head','Role_Lecturer','Role_TrainingDep')");


		/*
		 * http.authorizeRequests().antMatchers("/userInfo").access("hasRole('" +
		 * Constant.ROLE_STUDENT_MEMBER + "')");
		 * http.authorizeRequests().antMatchers("/admin").access("hasRole('" +
		 * Constant.ROLE_LECTURERS + "')");
		 */
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
		http.authorizeRequests().and().formLogin()
				.loginProcessingUrl("/j_spring_security_check")
				.loginPage("/login")
				.defaultSuccessUrl("/userInfo")
				.failureUrl("/login?error=true")
				.usernameParameter("username")
				.passwordParameter("password");
		http.authorizeRequests().and().logout().logoutUrl("/j_spring_security_logout").logoutSuccessUrl("/login?message=logout").deleteCookies("auth_code", "JSESSIONID").invalidateHttpSession(true);


		http.sessionManagement().maximumSessions(1).expiredUrl("/login").and().invalidSessionUrl("/login");



	}

    @Override
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }


}
