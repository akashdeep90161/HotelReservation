package com.innovationm.hotel.security;

import com.innovationm.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;


public class TokenBasedAuthorizationFilter extends BasicAuthenticationFilter {

    @Autowired
    private UserService userService;

    private HashMapUtils hashMapUtils;
    TokenBasedAuthorizationFilter(AuthenticationManager authenticationManager, UserService userService, HashMapUtils hashMapUtils) {
        super(authenticationManager);
        this.userService = userService;
        //	this.hashMap=new HashMap<>();
        //	hashMapUtils.setHashMap(this.hashMap);
        this.hashMapUtils=hashMapUtils;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException, UsernameNotFoundException {
        String url= request.getRequestURL().toString();
        String auth_user = request.getHeader("X_AUTH_USER");
        String Auth_Token = request.getHeader("X_AUTH_TOKEN");
        String hashMap_Token=hashMapUtils.getHashMap().get(auth_user);
        Set<GrantedAuthority> authority;
        if ((Auth_Token != null && Auth_Token.equals(hashMap_Token))) {
            authority= Collections.singleton(new SimpleGrantedAuthority("USER"));
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(auth_user, Auth_Token, authority));
        }
        else
        {
            String pass = request.getHeader("X_AUTH_PASS");
            if(pass!=null && auth_user!=null)
            {
                pass=HashMapUtils.encrypt(pass);
                UserDetails userDetails;
                String pass_dao;
                if(url.equals("http://localhost/user/register"))
                {
                    authority=Collections.singleton(new SimpleGrantedAuthority("USER"));
                    userDetails =new User(auth_user,pass,authority);
                    pass_dao=pass;
                }
                else
                {
                    userDetails=userService.loadUserByUsername(auth_user);
                    pass_dao=userDetails.getPassword();
                }

                if (pass.equals(pass_dao) || url.equals("http://localhost/user/register"))
                {

                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(auth_user,
                            pass,userDetails.getAuthorities()));


                    String token = hashMapUtils.getAlphaNumericString();
                    hashMapUtils.getHashMap().put(auth_user,token);

                    //`response.getWriter().write(token);
                    response.addHeader(HttpHeaders.AUTHORIZATION, token);

                }

            }

        }
        chain.doFilter(request, response);


    }
}
