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
//		jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT email, mot_de_passe, 1 FROM utilisateurs WHERE email=?");
		jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT pseudo, mot_de_passe, 1 FROM utilisateurs WHERE pseudo=?");
		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
				"SELECT u.pseudo, r.role FROM utilisateurs u INNER JOIN roles r ON r.is_admin = u.administrateur WHERE u.pseudo = ?");
	/*"SELECT u.email, r.IS_ADMIN FROM utilisateurs u INNER JOIN roles r ON r.IS_ADMIN = u.administrateur WHERE u.email=?");*/
		return jdbcUserDetailsManager;
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> {
			auth
			.requestMatchers("/").permitAll()
			.requestMatchers("/articles").permitAll()
			.requestMatchers("/css/*").permitAll()
			.requestMatchers("/js/*").permitAll()
			.requestMatchers("/images/*").permitAll()
			.requestMatchers("/encheres").permitAll()
			.requestMatchers("/profil/creer").permitAll()
			.requestMatchers("/articles/*").permitAll()
			.requestMatchers("/profil/session").permitAll()
			.requestMatchers("/encheres/*").permitAll()
			.requestMatchers("/error").permitAll()
			
			.requestMatchers("/voirListeUtilisateurs").hasAnyRole("ADMIN")
			.requestMatchers("/profil/desactiver/utilisateur").hasAnyRole("ADMIN")
			.requestMatchers("/profil/anonymiser/utilisateur").hasAnyRole("ADMIN")
			
			.requestMatchers("/profil").authenticated()
			.requestMatchers("/profil/view*").authenticated()
	        .anyRequest().authenticated();
		});
		http.formLogin(form -> {
			form.loginPage("/login").permitAll()
			.defaultSuccessUrl("/profil/session").permitAll();
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
