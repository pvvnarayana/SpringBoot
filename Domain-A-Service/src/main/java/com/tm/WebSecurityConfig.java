package com.tm;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		((InMemoryUserDetailsManagerConfigurer) auth.inMemoryAuthentication()
				.passwordEncoder(NoOpPasswordEncoder.getInstance())).withUser("admin").password("admin")
						.authorities(new String[] { "ADMIN" });
	}

	protected void configure(HttpSecurity http) throws Exception {
		((HttpSecurity) ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) ((HttpSecurity) http.csrf().disable())
				.authorizeRequests().anyRequest()).authenticated().and()).httpBasic();
	}
}
