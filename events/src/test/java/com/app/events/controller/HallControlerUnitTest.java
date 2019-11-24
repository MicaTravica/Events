package com.app.events.controller;

import com.app.events.dto.HallDTO;
import com.app.events.mapper.HallMapper;
import com.app.events.model.Hall;
import com.app.events.model.Place;
import com.app.events.security.TokenUtils;
import com.app.events.service.HallService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.ResourceAccessException;

import java.util.HashSet;
import java.util.Optional;

/*
    how to mock HallMapper ---> it is static method..
*/ 

@Import({authConfig.class, TokenUtils.class})
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HallController.class)
public class HallControlerUnitTest {

    public static long HALL_FIND_ID = 1L;
    public static long INVALID_HALL_ID = -1L;
    public static long PLACE_ID = 1L;

    public static ObjectMapper mapper = new ObjectMapper();

    @Autowired
	private MockMvc mockMvc;

    @MockBean
    private HallService hallService;

    @Before
    public void setUp() throws Exception{

        Mockito.when(hallService.findOne(HALL_FIND_ID)).thenReturn
            (new Hall(HALL_FIND_ID, "findHall", new Place(PLACE_ID),new HashSet<>(), new HashSet<>()));
        
        Mockito.when(hallService.findOne(INVALID_HALL_ID)).thenThrow(new ResourceAccessException("Hall not found"));
    }


    @Test
    @WithMockUser(username = "dusan", password = "bucan", roles = {"REGULAR"})
    public void whenValidId_thenFoundHall() throws Exception{
        mockMvc.perform(get("/api/hall/"+ HALL_FIND_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(HALL_FIND_ID))
                .andExpect(jsonPath("$.name").value("findHall"));
    }

    @Test
    @WithMockUser(username = "milovica", password = "cao", roles = {"ADMIN"})
    public void whenInvalidId_thenThowException() throws Exception{
        
        MvcResult result =  mockMvc.perform(get("/api/hall/"+ INVALID_HALL_ID))
                .andExpect(status().is4xxClientError())
                .andReturn();
                
        // Optional<ResourceNotFoundException> someException =
        //     Optional.ofNullable((ResourceNotFoundException) result.getResolvedException());

        // someException.ifPresent( (se) -> assertNotNull(se));
    }

    @Ignore
    @Test
    public void whenAddValidHall_thenHallShouldBeAdded() throws Exception
    {
        Hall hall = new Hall(
            HALL_FIND_ID, "findHall",
            new Place(PLACE_ID),
            new HashSet<>(), new HashSet<>());
        
        HallDTO content = HallMapper.toDTO(hall);

        System.out.println(mapper.writeValueAsString(content));
        System.out.println("---------------------------------");

        Mockito.when(hallService.create(hall)).thenReturn(hall);

        MvcResult result = mockMvc.perform(post("/api/hall")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(mapper.writeValueAsString(content)))
                                .andExpect(status().isCreated())
                                .andReturn();
        System.out.println( result.getResponse().getStatus());

        // Mockito.verify(hallService.create(hall), 1);
    }





}