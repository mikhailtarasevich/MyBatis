package com.mt.controller;

import com.mt.dto.RegionDto;
import com.mt.service.RegionService;
import jdk.net.SocketFlow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RegionControllerTest {

    @InjectMocks
    RegionController regionController;

    @Mock
    RegionService regionService;

    MockMvc mockMvc;

    private final String API_PATH = "/api/v1/regions";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(regionController)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @Test
    void getAllRegions_returnsDtoList() throws Exception {
        RegionDto regionDto = RegionDto.builder()
                .withId(1)
                .withName("Moscow")
                .withAbbr("MSK")
                .build();

        when(regionService.findAll()).thenReturn(Collections.singletonList(regionDto));

        mockMvc.perform(MockMvcRequestBuilders.get(API_PATH))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"Moscow\", \"abbr\":\"MSK\"}]"));

        verify(regionService, times(1)).findAll();
        verifyNoMoreInteractions(regionService);
    }

    @Test
    void getById_validId_returnsDto() throws Exception {
        int id = 1;

        RegionDto regionDto = RegionDto.builder()
                .withId(1)
                .withName("Moscow")
                .withAbbr("MSK")
                .build();

        when(regionService.findById(id)).thenReturn(regionDto);

        mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + "/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Moscow\", \"abbr\":\"MSK\"}"));

        verify(regionService, times(1)).findById(id);
        verifyNoMoreInteractions(regionService);
    }

    @Test
    void createRegion_validDto_returnsSavedDto() throws Exception {
        RegionDto regionDtoBeforeSave = RegionDto.builder()
                .withName("Moscow")
                .withAbbr("MSK")
                .build();

        RegionDto regionDtoAfterSave = RegionDto.builder()
                .withId(1)
                .withName("Moscow")
                .withAbbr("MSK")
                .build();

        when(regionService.create(regionDtoBeforeSave)).thenReturn(regionDtoAfterSave);

        mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Moscow\", \"abbr\":\"MSK\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Moscow\", \"abbr\":\"MSK\"}"));

        verify(regionService, times(1)).create(regionDtoBeforeSave);
        verifyNoMoreInteractions(regionService);
    }

    @Test
    void updateRegion_validRegionDto_returnsUpdatedRegion() throws Exception {
        RegionDto regionDto = RegionDto.builder()
                .withId(1)
                .withName("Moscow")
                .withAbbr("MSK")
                .build();

        when(regionService.update(regionDto)).thenReturn(regionDto);

        mockMvc.perform(MockMvcRequestBuilders.put(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1, \"name\":\"Moscow\", \"abbr\":\"MSK\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1, \"name\":\"Moscow\", \"abbr\":\"MSK\"}"));

        verify(regionService, times(1)).update(regionDto);
        verifyNoMoreInteractions(regionService);
    }

    @Test
    void deleteRegion_validId_returnsNothing () throws Exception {
        int id = 1;

        doNothing().when(regionService).delete(id);

        mockMvc.perform(MockMvcRequestBuilders.delete(API_PATH + "/{id}", id))
                .andExpect(status().isOk());

        verify(regionService, times(1)).delete(id);
        verifyNoMoreInteractions(regionService);
    }

}
