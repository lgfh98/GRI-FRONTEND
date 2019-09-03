package co.edu.uniquindio.gri.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Clase WebSecurityConfig.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    /* (non-Javadoc)
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure
     * (org.springframework.security.config.annotation.web.builders.HttpSecurity)
     * Se permite el acceso a librerías y complementos. Se restringe el acceso al inventario a los administradores.
	 * Se restringe el uso del resto de páginas web a clientes autenticados. 
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.csrf().disable()
            .authorizeRequests()
            .antMatchers("/css/**", "/js/**", "/img/**","/webjars/**" ).permitAll()
            .antMatchers("/inventario", "/reporteinventario").access("hasRole('ROLE_ADMIN')")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/")
                .failureUrl("/login?error")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
            .logout()
                .permitAll()
                .logoutSuccessUrl("/login?logout");
    }
    
    /** El encriptador de contraseñas. */
    BCryptPasswordEncoder bCryptPasswordEncoder;
    
    /**
     * Obtiene el objeto encargado de la encriptación y decriptación de contraseñas. 
     *
     * @return El objeto encargado de la encriptación y decriptación de contraseñas. 
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
		bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
        return bCryptPasswordEncoder;
    }
    
    /** Servicio encargado de obtener los detalles del usuario */
    @Autowired
    UserDetailsServiceImpl userDetailsService;
	
    /**
     * Configuración global. 
     *
     * @param auth, objeto encargado de construir el gestor de autenticaciones. 
     * @throws Exception en caso de encontrar algún fallo.
     */
    //Registra el service para usuarios y el encriptador de contrasena
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
 
        // Permite que el servicio encuentre el usuario en base de datos. 
        // Además agrega el encriptador de contraseñas. 
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());     
    }

}