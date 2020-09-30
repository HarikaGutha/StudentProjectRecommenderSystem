package com.prs.configurer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * LoginSecurityConfiguration is the class that extends
 * WebSecurityConfigurerAdapter and allows the customisation of web security and
 * HTTP security.
 * 
 * The class is annotated with @configuration to tag the class as a source of
 * bean and annotated with @EnableWebSecurity which allows to apply spring
 * security in the application
 * 
 * @author 190026870
 *
 */

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class LoginSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * configure(HttpSecurity http) method is used to configure Http security.
	 */

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/").hasAnyAuthority("Student", "Supervisor").antMatchers("/uploadProject")
				.hasAnyAuthority("Supervisor").antMatchers("/projectManagement").hasAnyAuthority("Supervisor")
				.antMatchers("/otherSupervisors").hasAnyAuthority("Supervisor").antMatchers("/savedProjects")
				.hasAnyAuthority("Student").antMatchers("/allProjects").hasAnyAuthority("Student")
				.antMatchers("/supervisors").hasAnyAuthority("Student").antMatchers("/projectProposal")
				.hasAnyAuthority("Student").antMatchers("/supervisorProfile").hasAnyAuthority("Student", "Supervisor")
				.anyRequest().authenticated().and().formLogin().loginPage("/login").defaultSuccessUrl("/dashboard")
				.permitAll().and().exceptionHandling().accessDeniedPage("/AccessDenied").and().rememberMe()
				.key("uniqueAndSecret").tokenValiditySeconds(2592000);

	}

	/**
	 * configure(WebSecurity web) method is used to configure Web security.
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**");
		web.ignoring().antMatchers("/images/**");
		web.ignoring().antMatchers("/resources/**");
		web.ignoring().antMatchers("/static/**");
		web.ignoring().antMatchers("/js/**");
		web.ignoring().antMatchers("/templates/**");
		web.ignoring().antMatchers("/students/**");
	}

	/**
	 * configure(AuthenticationManagerBuilder authenticationManagerBuilder) is used
	 * by the default implementation of authenticationManager() to attempt to obtain
	 * an AuthenticationManager.
	 * 
	 * documentation referred from spring.io
	 */
	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService);
	}

	/**
	 * getPasswordEncoder() is client side authentication for password encoder.
	 * 
	 * ---------Reason for using deprecated method--------------
	 * 
	 * this method is depreciated, and latest spring password encoder is
	 * BCryptPasswordEncoder but as there no registration form to encode passwords
	 * while saving in database BCryptPasswordEncoder is not used here for decoding.
	 * or As the passwords in the database are not encoded NoOpPasswordEncoder is
	 * used even if it is deprecated.
	 */
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
