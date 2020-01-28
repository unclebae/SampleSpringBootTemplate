package com.template.coe.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // Using query user schema
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//        .usersByUsernameQuery("select username, password, enabled from users where username = ?")
//        .authoritiesByUsernameQuery("select username, authority from authorities where username = ?");
//
//    }
//

    @Autowired
    UserDetailsService userDetailservice;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Using query user schema
        auth.userDetailsService(userDetailservice);

    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // Using default user schema
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                ;
//        ;
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .withDefaultSchema()
//                .withUser(
//                        User.withUsername("user")
//                                .password("pass")
//                                .roles("USER")
//                )
//                .withUser(
//                        User.withUsername("admin")
//                                .password("pass")
//                                .roles("ADMIN")
//                )
//        ;
//
//
//    }

    /**
     * / : All (unauthenticated)
     * /user : USER and Admin role
     * /admin : Admin role
     *
     *
     * http://localhost:8080/login?error
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
//                .antMatchers("/user").hasRole("USER")
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/", "static/css", "static/js").permitAll()
                .and().formLogin();

    }
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
