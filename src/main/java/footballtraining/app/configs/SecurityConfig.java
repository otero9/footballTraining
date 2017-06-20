package footballtraining.app.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import footballtraining.app.model.user.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserService userService;

	/**
	 * Remember me services.
	 *
	 * @return the token based remember me services
	 */
	@Bean
	public TokenBasedRememberMeServices rememberMeServices() {
		return new TokenBasedRememberMeServices("remember-me-key", (UserDetailsService) userService);
	}
	
	/**
	 * Password encoder.
	 *
	 * @return the password encoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new StandardPasswordEncoder();
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.csrf().disable()
        .authorizeRequests()
            .anyRequest().permitAll()
    		.and()
    		.formLogin()
    		.loginPage("/signin")
    		.permitAll()
    		.failureUrl("/signin?error=1")
    		.loginProcessingUrl("/authenticate")
    		.and()
    		.logout()
    		.logoutUrl("/logout")
    		.permitAll()
    		.logoutSuccessUrl("/signin?logout")
    		.and()
    		.rememberMe()
    		.rememberMeServices(rememberMeServices())
    		.key("remember-me-key");
    }
    
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.eraseCredentials(true)
		.userDetailsService((UserDetailsService)userService)
		.passwordEncoder(passwordEncoder());
	}
    
	@Bean(name = "authenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
    
}
