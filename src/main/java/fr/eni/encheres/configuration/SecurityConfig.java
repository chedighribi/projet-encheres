package fr.eni.encheres.configuration;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Bean
	UserDetailsManager userDetailsManager(DataSource dataSource) {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailsManager.setUsersByUsernameQuery("select email, mot_de_passe, 1 from UTILISATEURS where email=?");
		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
				"select u.email, r.IS_ADMIN from UTILISATEURS u INNER JOIN roles r ON r.IS_ADMIN = u.administrateur where u.email=?");
		return jdbcUserDetailsManager;
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> {
			auth
			.requestMatchers("/").permitAll()
			.requestMatchers("/css/*").permitAll()
			.requestMatchers("/js/*").permitAll()
			.requestMatchers("/images/*").permitAll()
			.requestMatchers("/encheres").permitAll()
			.requestMatchers("/profil*").permitAll()
			.requestMatchers("/profil/modifier").permitAll()
			.requestMatchers("/profil/creer").permitAll()
			.requestMatchers("/encheres/*").permitAll()
	        .anyRequest().authenticated();
		});
		http.formLogin(form -> {
			form.loginPage("/login").permitAll()
			.defaultSuccessUrl("/");
			});
		http.logout(logout -> logout
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.deleteCookies("JSESSIONID")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/")
				.permitAll()
				);
		return http.build();
	}
}
