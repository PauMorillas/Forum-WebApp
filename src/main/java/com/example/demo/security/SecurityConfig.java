package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(auth -> auth
						.requestMatchers("/", "/public/**", "/login", "/register", "/css/**", "/js/**", "/posts/**",
								"/likes/**")
						.permitAll()
				.anyRequest().authenticated()
				)
			.formLogin(form -> form
				.loginPage("/login")
				.usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true") // redirige si falla
				.permitAll()
			)
			.oauth2Login(oauth2 -> oauth2
				.loginPage("/login") // Html propio
						.defaultSuccessUrl("/")
				.failureUrl("/login?error=true")
			)
			.logout(logout -> logout
	                .logoutSuccessUrl("/")
	                .permitAll()
	            );

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder encoder,
			UserDetailsService userDetailsService) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsService)
				.passwordEncoder(encoder).and().build();
	}
}
