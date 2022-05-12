package com.bootcamp.microservicemeetup.controller;

import com.bootcamp.microservicemeetup.controller.resource.MeetupController;
import com.bootcamp.microservicemeetup.controller.dto.MeetupDTO;
import com.bootcamp.microservicemeetup.model.entity.Meetup;
import com.bootcamp.microservicemeetup.service.MeetupService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = {MeetupController.class})
@AutoConfigureMockMvc
public class MeetupControllerTest {

    static final String MEETUP_API = "/api/meetups";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private MeetupService meetupService;

    @Test
    @DisplayName("Should register on a meetup")
    public void createMeetupTest() throws Exception {

        MeetupDTO dto = MeetupDTO.builder().nameMeetup("Womakerscode Dados").dateMeetup("10/10/2021").build();
        String json = new ObjectMapper().writeValueAsString(dto);

        Meetup meetup = Meetup.builder().idMeetup(11).nameMeetup("Womakerscode Dados").dateMeetup("10/10/2021").build();

        BDDMockito.given(meetupService.save(Mockito.any(Meetup.class))).willReturn(meetup);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(MEETUP_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("idMeetup").value(meetup.getIdMeetup()))
                .andExpect(jsonPath("nameMeetup").value(meetup.getNameMeetup()))
                .andExpect(jsonPath("dateMeetup").value(meetup.getDateMeetup()));
    }

    @Test
    @DisplayName("Should get all meetups with pageable and filters")
    public void getAllMeetupsTest() throws Exception {
        Integer idMeetup = 11;

        Meetup meetup = Meetup.builder()
                .idMeetup(idMeetup)
                .nameMeetup(createNewMeetup().getNameMeetup())
                .dateMeetup(createNewMeetup().getDateMeetup()).build();

        BDDMockito.given(meetupService.find(Mockito.any(Meetup.class), Mockito.any(Pageable.class)) )
                .willReturn(new PageImpl<Meetup>(Arrays.asList(meetup), PageRequest.of(0,100), 1));

        String queryString = String.format("?nameEvent=%s&dateMeetup=%s&page=0&size=100",
                meetup.getIdMeetup(), meetup.getDateMeetup());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(MEETUP_API.concat(queryString))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("content", hasSize(1)))
                .andExpect(jsonPath("totalElements"). value(1))
                .andExpect(jsonPath("pageable.pageSize"). value(100))
                .andExpect(jsonPath("pageable.pageNumber"). value(0));
    }

    private MeetupDTO createNewMeetup() {
        return  MeetupDTO.builder().idMeetup(101).nameMeetup("WhoMakersCode Bootcamp Java").dateMeetup("01/05/2022").build();
    }


}
