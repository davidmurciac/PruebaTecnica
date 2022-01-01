package co.gov.colombiacompra.tienda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import co.gov.colombiacompra.tienda.security.JWTAuthorizationFilter;


/**
 * Clase que representa la aplicación tienda y que contiene las configuraciones 
 * de la misma basada en el Framework de Spring 
 * 
 * @author David.Murcia
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = "co.gov.colombiacompra.tienda")
public class TiendaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiendaApplication.class, args);
	}
	
	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
				.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()			
				.antMatchers(HttpMethod.POST, "/usuario/login").permitAll()
				.antMatchers(HttpMethod.POST, "/usuario").permitAll()
				.anyRequest().authenticated()
				
				;
		}
	}

}
