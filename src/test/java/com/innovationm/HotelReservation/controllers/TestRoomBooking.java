package com.innovationm.HotelReservation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innovationm.hotel.model.primary.RoomRequest;
import com.innovationm.hotel.model.primary.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.innovationm.hotel.HotelReservationApplication.class)
@WebAppConfiguration
public class TestRoomBooking {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;
    private JacksonTester<RoomRequest> jsonRoomRequest;
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
    public void testBookRoom() throws Exception {

        RoomRequest roomRequest=new RoomRequest();

        ResultActions response=mvc
                .perform(post("/booking/bookRoom").param("roomId","47").param("userId","28")
                        .header("X_AUTH_USER","rashik@gmail.com").header("X_AUTH_TOKEN",Auth_Token)
                .contentType(APPLICATION_JSON)
                .content(jsonRoomRequest.write(roomRequest).getJson()))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void testGetRoomRequestList() throws Exception {
        ResultActions response=mvc
                .perform(get("/booking/getBookedRoomList").param("userId","21")
                .header("X_AUTH_USER","rashik@gmail.com").header("X_AUTH_TOKEN",Auth_Token)
                .contentType(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void testCancelRoomBooking() throws Exception {
        ResultActions response=mvc
                .perform(delete("/booking/cancelRoomBooking").param("roomId","47").param("userId","28")
                .header("X_AUTH_USER","rashik@gmail.com").header("X_AUTH_TOKEN",Auth_Token)
        .contentType(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is(200));
    }
    @Test
    public void testUpdateRoomBooking() throws Exception
    {
        RoomRequest roomRequest =new RoomRequest();

        ResultActions response=mvc
                .perform(put("/booking/updateBooking").param("roomRequestId","63")
                        .header("X_AUTH_USER","rashik@gmail.com").header("X_AUTH_TOKEN",Auth_Token)
                .contentType(APPLICATION_JSON)
                .content(jsonRoomRequest.write(roomRequest).getJson()))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is(200));

    }
}
