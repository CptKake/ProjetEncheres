package fr.eni.tp.configuration;

	import javax.sql.DataSource;

	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.security.config.Customizer;
	import org.springframework.security.config.annotation.web.builders.HttpSecurity;
	import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
	import org.springframework.security.core.userdetails.User;
	import org.springframework.security.core.userdetails.UserDetails;
	import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
	import org.springframework.security.crypto.password.PasswordEncoder;
	import org.springframework.security.provisioning.InMemoryUserDetailsManager;
	import org.springframework.security.provisioning.JdbcUserDetailsManager;
	import org.springframework.security.provisioning.UserDetailsManager;
	import org.springframework.security.web.SecurityFilterChain;
	import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

	@Configuration
	@EnableWebSecurity
	public class SecurityConfig {

		
		
		@Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			http
				.authorizeHttpRequests((authorize) -> authorize
					.requestMatchers("/*").permitAll()
					.requestMatchers("/css/*").permitAll()
					.requestMatchers("/images/*").permitAll()
					.requestMatchers("/login").permitAll()
					.requestMatchers("/logout").permitAll()
					.anyRequest().permitAll() // interdit l'accès aux urls non configurées
				)
				.httpBasic(Customizer.withDefaults())
				.formLogin((formLogin) ->
					formLogin
						.loginPage("/login")
						.defaultSuccessUrl("/encheres")
				)
				.logout((logout) ->
					logout
						.invalidateHttpSession(true)
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
						.logoutSuccessUrl("/encheres")
				);

				
				;
			
			
			


			return http.build();
		}
		
		
		
		  @Bean
		  public PasswordEncoder passwordEncoder() {
		        return new BCryptPasswordEncoder();
		    }
		
		@Bean
	    UserDetailsManager users(DataSource dataSource) {
	        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

	        // Requête pour récupérer les utilisateurs
	        jdbcUserDetailsManager.setUsersByUsernameQuery(
	            "SELECT  pseudo AS username, mot_de_passe as password, 'true' AS enabled FROM utilisateurs WHERE pseudo = ?"
	        );

	        // Requête pour récupérer les autorités (rôles)
	        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
	        		"SELECT pseudo AS username, administrateur AS authority " +
	        	            "FROM utilisateurs WHERE pseudo = ? "

	        );
	        return jdbcUserDetailsManager;
		}
		
		
		
		
		
	}
