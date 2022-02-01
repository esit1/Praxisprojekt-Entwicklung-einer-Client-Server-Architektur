package de.server.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static de.server.security.JWTAuthenticationFilter.LOGIN;


/**
 * Class extends the WebSecurityConfigurerAdapter.
 */
@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    /**
     * goodsRepository to handle user detail information.
     */
    CustomUserDetailsService userDetailsService;

    /**
     * The application context.
     */
    @Autowired
    private ApplicationContext context;

    /**
     * The PasswordEncoder that uses the BCrypt strong hashing function
     */
    BCryptPasswordEncoder bCryptPasswordEncoder;


    /**
     * Override The method configure to configure the HttpSecurity,
     *
     * @param http the HttpSecurity to modify
     * @throws Exception if an error occurs
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, LOGIN).permitAll().anyRequest().authenticated().and().addFilter(new JWTAuthenticationFilter(authenticationManager(), context)).addFilter(new JWTAuthorizationFilter(authenticationManager())).sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * return a corsFilter.
     *
     * @return the CorsFilter.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

    /**
     * returns the bCryptPasswordEncoder.
     *
     * @return the bCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
