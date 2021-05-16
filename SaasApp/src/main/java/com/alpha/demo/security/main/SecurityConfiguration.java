package com.alpha.demo.security.main;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.alpha.demo.security.main.MyUserDetailsService;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;
    private MyUserDetailsService myUserDetailsService;

    public SecurityConfiguration(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .antMatchers("/index.html").permitAll()
        .antMatchers("/profile/**").authenticated()
        .antMatchers("/home/**").hasRole("ADMIN")
        .antMatchers("/cust/**").hasRole("ADMIN")
        .antMatchers("/deal/**").hasRole("ADMIN")
        .antMatchers("/pro/**").hasRole("ADMIN")
        .antMatchers("/tax/**").hasRole("ADMIN")
        .antMatchers("/service/**").hasRole("ADMIN")
        .antMatchers("/creditDebitAmo/**").hasRole("ADMIN")
        .antMatchers("/creditAmo/**").hasRole("ADMIN")
        .antMatchers("/debitAmo/**").hasRole("ADMIN")
        .antMatchers("/management/**").hasAnyRole("ADMIN", "MANAGER")
        .antMatchers("/api/public/test1").hasAuthority("ACCESS_TEST1")
        .antMatchers("/api/public/test2").hasAuthority("ACCESS_TEST2")
        .antMatchers("/api/public/users").hasRole("ADMIN")
        .and()
        .formLogin().loginProcessingUrl("/signin")
		.loginPage("/login").permitAll()
		.usernameParameter("txtUsername")
        .passwordParameter("txtPassword")
        .and()
        .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
        .and().exceptionHandling().accessDeniedPage("/accessdenied").and()
        .rememberMe().tokenValiditySeconds(2592000).key("mySecret!").rememberMeParameter("checkRememberMe");
        http.cors().and().csrf().disable();
    }

//    @Bean
//    public PasswordEncoder getPasswordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }
    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.myUserDetailsService);

        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
