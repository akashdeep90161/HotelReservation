package com.innovationm.HotelReservation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innovationm.hotel.model.primary.User;
import com.innovationm.hotel.model.secondary.Location;
import com.innovationm.hotel.security.HashMapUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.runner.RunWith;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Locale;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.innovationm.hotel.HotelReservationApplication.class)
@WebAppConfiguration
public class TestUser {
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;
    private JacksonTester<User> jsonUser;
    private String Auth_Token;

    @Before
    public void setup() throws Exception
    {
        JacksonTester.initFields(this, new ObjectMapper()); // this line intilized JacksonTester<User> jsonUser reference
        mvc= MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity()).build();
        ResultActions resultActions=mvc.perform(get("/user/login").header("X_AUTH_USER","rashik@gmail.com").header("X_AUTH_PASS","qwerty")
                .contentType(APPLICATION_JSON))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().is(200));

        Auth_Token= resultActions.andReturn().getResponse().getHeader(HttpHeaders.AUTHORIZATION);
    }

    @Test
    public void testGetUserList() throws Exception {
        ResultActions response = mvc
                .perform(get("/user/getUserList").header("X_AUTH_USER","abc@gmail.com").header("X_AUTH_TOKEN",Auth_Token)
                        .contentType(APPLICATION_JSON))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void testRegisterUser() throws Exception {
        User user=new User();
        String email=HashMapUtils.getAlphaNumericString()+"@gmail.com";
        user.setEmail(email);
        user.setFirstName("Akash "+HashMapUtils.getAlphaNumericString());
        user.setLastName(HashMapUtils.getAlphaNumericString());
        user.setPass("qwerty");
        user.setPhone("8755282321");

        Location location=new Location();
        location.setLatitude(13554323.8);
        location.setLongitude(135543.323);
        user.setLocation(location);

        ResultActions response=mvc
                .perform(post("/user/register").header("X_AUTH_USER",email).header("X_AUTH_PASS","qwerty")
                .contentType(APPLICATION_JSON)
                .content(jsonUser
                        .write(user)
                        .getJson()))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is(200));
    }
    @Test
    public void testGetUser() throws Exception {
        ResultActions response=mvc
                .perform(get("/user/getUserById").param("userId","28")
                        .header("X_AUTH_USER","rashik@gmail.com").header("X_AUTH_TOKEN",Auth_Token)
                .contentType(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is(200));
    }
    @Test
    public void testUpdateUser() throws Exception
    {
        User user=new User();
        String email=HashMapUtils.getAlphaNumericString()+"@gmail.com";
        user.setEmail(email);
        user.setFirstName("Akash "+HashMapUtils.getAlphaNumericString());
        user.setLastName(HashMapUtils.getAlphaNumericString());
        user.setPass("qwerty");
        user.setPhone("8755282321");

        Location location=new Location();
        location.setLatitude(13554323.8);
        location.setLongitude(135543.323);
        user.setLocation(location);

        ResultActions response=mvc
                .perform(put("/user/updateProfile").param("userId","28")
                        .header("X_AUTH_USER","rashik@gmail.com").header("X_AUTH_TOKEN",Auth_Token)
                .contentType(APPLICATION_JSON)
                .content(jsonUser.write(user).getJson()))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is(200));
    }
    @Test
    public void testDeleteProfile() throws Exception
    {
      ResultActions response=mvc
      .perform(delete("/user/deletProfile").param("userId","28")
              .header("X_AUTH_USER","rashik@gmail.com").header("X_AUTH_TOKEN",Auth_Token)
      .contentType(APPLICATION_JSON))
              .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is(200));
    }



}
