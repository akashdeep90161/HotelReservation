package com.innovationm.hotel.security;

import com.innovationm.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private UserService userService;

    @Autowired
    private HashMapUtils hashMapUtils;

    @Bean
    public PasswordEncoder passwordEncoder() throws NoSuchAlgorithmException {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception
    {
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder());
        // authenticationManagerBuilder.userDetailsService(bookDetailsService).passwordEncoder(passwordEncoder());
    }
    private SecurityExpressionHandler<FilterInvocation> webExpressionHandler() {
        DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
       // defaultWebSecurityExpressionHandler.setRoleHierarchy(roleHierarchy());
        return defaultWebSecurityExpressionHandler;
    }

    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);

        http
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable()
                //       .addFilter(new TokenBasedAuthenticationFilter(authenticationManager()))
                .addFilter(new TokenBasedAuthorizationFilter(authenticationManager(),userService,hashMapUtils));

        http.authorizeRequests()
                .expressionHandler(webExpressionHandler())
                //   .antMatchers("/sendSMS/{id}/**").permitAll();
                //  .antMatchers(ApiURL.USER_CONTROLLER_LOGIN).denyAll();
                //  .antMatchers("/**").hasAuthority("READER");
                .antMatchers("/**").authenticated();

    }

}
