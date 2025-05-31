package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll()).csrf(csrf -> csrf.disable()); // solo para pruebas
//		return http.build();
//	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/", "/public/**", "/login", "/css/**", "/js/**").permitAll()
				.anyRequest().authenticated()
				)
			.formLogin(form -> form
				.loginPage("/login")
				.permitAll()
			)
			.oauth2Login(oauth2 -> oauth2
				.loginPage("/login") // Html propio
				.defaultSuccessUrl("/dashboard")
				.failureUrl("/login?error=true")
			);

		return http.build();
	}
}
