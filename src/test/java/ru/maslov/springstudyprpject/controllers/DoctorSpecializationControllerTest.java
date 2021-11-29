package ru.maslov.springstudyprpject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.maslov.springstudyprpject.entities.DoctorsSpecialization;
import ru.maslov.springstudyprpject.servicies.DoctorsSpecializationService;

import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.maslov.springstudyprpject.utils.JsonObjectMapper.asJsonString;

@SpringBootTest
@AutoConfigureMockMvc
class DoctorSpecializationControllerTest {

    public static final String BASE_PATH = "/specializations";

    @MockBean
    private DoctorsSpecializationService doctorsSpecializationService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void canGetSpecializations() throws Exception {
        //given
        List<DoctorsSpecialization> specializations
                = List.of(new DoctorsSpecialization("test_1", new HashSet<>()),
                new DoctorsSpecialization("test_1", new HashSet<>()));
        int expectedSize = specializations.size();
        HttpStatus expectedStatus = HttpStatus.OK;
        Mockito.doReturn(specializations)
                .when(doctorsSpecializationService)
                .getAllSpecializations();
        //when
        //then
        this.mockMvc.perform(get(BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(expectedSize)));
        verify(doctorsSpecializationService).getAllSpecializations();
    }

    @Test
    void canSaveSpecialization() throws Exception {
        //given
        DoctorsSpecialization doctorsSpecialization = new DoctorsSpecialization("test_spec", new HashSet<>());
        Mockito.doReturn(doctorsSpecialization)
                .when(doctorsSpecializationService)
                .saveSpecialization(doctorsSpecialization);
        //when
        //then
        this.mockMvc.perform(post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(doctorsSpecialization)))
                .andExpect(status().isOk());
        verify(doctorsSpecializationService).saveSpecialization(doctorsSpecialization);
    }

    @Test
    void canNotSaveSpecializationBecauseWrongContentType() throws Exception {
        //given
        DoctorsSpecialization doctorsSpecialization =
                new DoctorsSpecialization("test_spec", new HashSet<>());
        //when
        //then
        this.mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_ATOM_XML)
                        .content(asJsonString(doctorsSpecialization)))
                .andExpect(status().isUnsupportedMediaType());
        verify(doctorsSpecializationService, never()).saveSpecialization(doctorsSpecialization);
    }

    @Test
    void canNotSaveSpecializationBecauseBodyIsNull() throws Exception {
        //given
        DoctorsSpecialization doctorsSpecialization = null;
        //when
        //then
        this.mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(doctorsSpecialization)))
                .andExpect(status().isBadRequest());
        verify(doctorsSpecializationService, never()).saveSpecialization(doctorsSpecialization);
    }

    @Test
    void canNotSaveSpecializationBecauseNameOfSpecializationIsNul() throws Exception{
        //given
        DoctorsSpecialization doctorsSpecialization =
                new DoctorsSpecialization(null, new HashSet<>());
        //when
        //then
        this.mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(doctorsSpecialization)))
                .andExpect(status().isBadRequest());
        verify(doctorsSpecializationService, never()).saveSpecialization(doctorsSpecialization);
    }

    @Test
    void canNotGetSpecializationsBecauseWrongHttpMethod() throws Exception {
        //given
        HttpStatus expectedStatus = HttpStatus.METHOD_NOT_ALLOWED;
        //when
        //then
        this.mockMvc.perform(put(BASE_PATH))
                .andExpect(status().isMethodNotAllowed());
        verify(doctorsSpecializationService, never()).getAllSpecializations();
    }


}