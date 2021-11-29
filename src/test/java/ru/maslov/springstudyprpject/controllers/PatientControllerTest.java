package ru.maslov.springstudyprpject.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.maslov.springstudyprpject.dto.mappers.PatientMapper;
import ru.maslov.springstudyprpject.entities.Patient;
import ru.maslov.springstudyprpject.entities.Role;
import ru.maslov.springstudyprpject.exceptions.PatientDataValidationException;
import ru.maslov.springstudyprpject.exceptions.PatientNotFoundException;
import ru.maslov.springstudyprpject.servicies.PatientService;

import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.maslov.springstudyprpject.utils.JsonObjectMapper.asJsonString;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {

    public static final String BASE_PATH = "/patients";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatientMapper patientMapper;

    @MockBean
    private PatientService patientService;

    @Test
    void canGetAllPatients() throws Exception {
        //given
        List<Patient> patients = List.of(new Patient(), new Patient(), new Patient());
        int expectedListSize = patients.size();
        Mockito.doReturn(patients)
                .when(patientService)
                .getPatients();
        //when
        //then
        mockMvc.perform(get(BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(expectedListSize)));
        verify(patientService).getPatients();
    }

    @Test
    void canGetPatientById() throws Exception {
        //given
        Long id = 8L;
        Patient testPatient = new Patient();
        testPatient.setId(id);
        Mockito.doReturn(testPatient)
                .when(patientService)
                .getById(id);
        //when
        //then
        mockMvc.perform(get(BASE_PATH + "/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("8"));
        verify(patientService).getById(id);
    }

    @Test
    void canNotGetPatientByIdBecauseNoSuchPatientInDB() throws Exception {
        //given
        Long id = 8L;
        doThrow(PatientNotFoundException.class)
                .when(patientService).getById(id);
        //when
        //then
        mockMvc.perform(get(BASE_PATH + "/" + id))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("patient doesn't found")));
        verify(patientService).getById(id);
    }

    @Test
    void canNotManipulateWithResourcesBecauseWrongHttpMethod() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(delete(BASE_PATH))
                .andExpect(status().isMethodNotAllowed());
        verify(patientService, never()).getPatients();
    }

    @Test
    void canDeletePatientById() throws Exception {
        //given
        Long id = 8L;
        Patient testPatient = new Patient();
        testPatient.setId(id);
        given(patientService.getById(id))
                .willReturn(testPatient);
        //when
        //then
        mockMvc.perform(delete(BASE_PATH + "/" + id))
                .andExpect(status().isOk());
        verify(patientService).deleteById(id);
    }

    @Test
    void canNotDeletePatientByIdBecauseNoSuchPatientInDB() throws Exception {
        //given
        Long wrongId = 1L;
        doThrow(PatientNotFoundException.class)
                .when(patientService).deleteById(wrongId);
        //when
        //then
        mockMvc.perform(delete(BASE_PATH + "/" + wrongId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("patient")));
        verify(patientService).deleteById(wrongId);
    }

    @Test
    void canSaveNewPatient() throws Exception {
        //given
        Long id = 12L;
        String expectedName = "Иван";
        Patient patient = new Patient("Иван",
                "Сергеевич",
                "Тюрин",
                "tuirnis",
                "1vaN",
                "79106272030",
                List.of(Role.PATIENT),
                "1234567890123456",
                new HashSet<>());
        patient.setId(id);
        given(patientService.save(patient))
                .willReturn(patient);
        //when
        //then
        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(patient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(expectedName));
        verify(patientService).save(patient);
    }

    @Test
    void canNotSavePatientBecauseHasNullFields() throws Exception {
        //given
        Patient patient = new Patient(null,
                "Сергеевич",
                "Тюрин",
                "tuirnis",
                "1vaN",
                "79106272030",
                List.of(Role.PATIENT),
                "1234567890123456",
                new HashSet<>());
        //when
        //then
        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(patient)))
                .andExpect(status().isBadRequest());
        verify(patientService, never()).save(patient);
    }

    @Test
    void canNotSavePatientBecauseBodyIsNull() throws Exception {
        //given
        Patient patient = null;
        //when
        //then
        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(patient)))
                .andExpect(status().isBadRequest());
        verify(patientService, never()).save(patient);
    }

    @Test
    void canNotSavePatientBecauseExistOtherPatientWithSameLoginOrPolicyNumOrPhoneNum() throws Exception {
        //given
        Patient patient = new Patient("Иван",
                "Сергеевич",
                "Тюрин",
                "tuirnis",
                "1vaN",
                "79106272030",
                List.of(Role.PATIENT),
                "1234567890123456",
                new HashSet<>());

        doThrow(PatientDataValidationException.class)
                .when(patientService).save(patient);
            //when
        //then
        mockMvc.perform(post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(patient)))
                .andExpect(status().isUnprocessableEntity());
        verify(patientService).save(patient);
    }
}