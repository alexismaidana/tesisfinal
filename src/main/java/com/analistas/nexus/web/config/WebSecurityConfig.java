package com.analistas.nexus.web.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/* import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager; */
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true
)
public class WebSecurityConfig {
    
    @Autowired
    DataSource dataSource;

    @Bean
    BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.authorizeHttpRequests().antMatchers("/", "/home", "/css/**", "/js/**", "/img/**", "/registro/**", 
        "/clientes/nuevo/**", "/clientes/guardar/**", "/contacto/**", "/producto/**", "/categorias/**", "/conocenos/**", "/detalleProducto/**")
            .permitAll().anyRequest().permitAll()
            .and()
            .formLogin().loginPage("/login")
                        .permitAll()
            .and()
            .logout().permitAll()
            .and()
            .formLogin().loginPage("/login").failureUrl("/login?error").permitAll()
            .and()
            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/home")
            .and()
            .build();
    }

   /*  @Bean
    public UserDetailsService userDetailsService () {
        UserDetails user = User
            .withDefaultPasswordEncoder()
            .username("elian")
            .password("1234")
            .roles("USER")
            .build();
            UserDetails admin = User
            .withDefaultPasswordEncoder()
            .username("alexis")
            .password("1234")
            .roles("ADMIN")
            .build();
        return new InMemoryUserDetailsManager(user, admin);
    } */

    @Autowired
    public void configGlobal(AuthenticationManagerBuilder builder) throws Exception {

        //Autenticación con JDBC:
        builder
            .jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery("select nombre, clave, activo from usuarios where nombre = ?")
            .authoritiesByUsernameQuery("select u.nombre, p.nombre from permisos p "
                + "inner join usuarios u on "
                + "u.id_permiso = p.id where u.nombre = ?");

                
    }

}