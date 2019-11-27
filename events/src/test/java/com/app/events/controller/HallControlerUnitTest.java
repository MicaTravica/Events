package com.app.events.controller;

import com.app.events.dto.HallDTO;
import com.app.events.mapper.HallMapper;
import com.app.events.model.Hall;
import com.app.events.model.Place;
import com.app.events.service.HallService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.ResourceAccessException;

import java.util.HashSet;

/*
    how to mock HallMapper ---> it is static method..
*/ 

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@WithMockUser(username = "dusan", password = "bucan", roles = {"ADMIN", "REGULAR"})
public class HallControlerUnitTest {

    public static long HALL_FIND_ID = 1L;
    public static long INVALID_HALL_ID = -1L;
    public static long PLACE_ID = 1L;

    public static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private HallService hallService;

    @Before
    public void setUp() throws Exception{

        Mockito.when(hallService.findOne(HALL_FIND_ID)).thenReturn
            (new Hall(HALL_FIND_ID, "findHall", new Place(PLACE_ID),new HashSet<>(), new HashSet<>()));
        
        Mockito.when(hallService.findOne(INVALID_HALL_ID)).thenThrow(new ResourceAccessException("Hall not found"));
    }


    // sad vise nije do config-a jer si ga izmenio, kad skines preAuth ono prolazi...
    //
    @Test()
    
    public void whenValidId_thenFoundHall() throws Exception{

        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(a.getName());


        ResponseEntity<HallDTO> responseEntity =
            restTemplate.getForEntity("/api/hall/1",HallDTO.class);

        HallDTO hallDTO = responseEntity.getBody();
        System.out.println(hallDTO);



        // mockMvc.perform(get("/api/hall/"+ HALL_FIND_ID))
        //         .andExpect(status().isOk())
        //         .andExpect(jsonPath("$.id").value(HALL_FIND_ID))
        //         .andExpect(jsonPath("$.name").value("findHall"));
    }

    @Ignore
    @Test
    @WithMockUser(username = "milovica", password = "cao", roles = {"ADMIN"})
    public void whenInvalidId_thenThowException() throws Exception{
        
        // MvcResult result =  mockMvc.perform(get("/api/hall/"+ INVALID_HALL_ID))
        //         .andExpect(status().is4xxClientError())
        //         .andReturn();
                
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

        // MvcResult result = mockMvc.perform(post("/api/hall")
        //                                 .contentType(MediaType.APPLICATION_JSON)
        //                                 .content(mapper.writeValueAsString(content)))
        //                         .andExpect(status().isCreated())
        //                         .andReturn();
        // System.out.println( result.getResponse().getStatus());

        // Mockito.verify(hallService.create(hall), 1);
    }





}