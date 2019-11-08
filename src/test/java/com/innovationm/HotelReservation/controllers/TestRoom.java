package com.innovationm.HotelReservation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innovationm.hotel.model.primary.Room;
import com.innovationm.hotel.model.primary.RoomRequest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.innovationm.hotel.HotelReservationApplication.class)
@WebAppConfiguration
public class TestRoom {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;
    private JacksonTester<Room> jsonRoom;
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
    public void testCreateRoom() throws Exception
    {
        Room room =new Room();
        ResultActions  rsponse=mvc
                .perform(post("/room/addRoom")
                        .header("X_AUTH_USER","rashik@gmail.com").header("X_AUTH_TOKEN",Auth_Token)
                .content(jsonRoom.write(room).getJson())
                .contentType(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is(200));
    }
    @Test
    public void testGetAllRooms() throws Exception
    {
            ResultActions response=mvc
                    .perform(get("/room/getAllRooms")
                            .header("X_AUTH_USER","rashik@gmail.com").header("X_AUTH_TOKEN",Auth_Token)
                    .contentType(APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is(200));
    }

}


