package ru.maslov.springstudyprpject.servicies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.maslov.springstudyprpject.entities.Appointment;
import ru.maslov.springstudyprpject.entities.Doctor;
import ru.maslov.springstudyprpject.entities.Patient;
import ru.maslov.springstudyprpject.exceptions.PatientDataValidationException;
import ru.maslov.springstudyprpject.exceptions.PatientNotFoundException;
import ru.maslov.springstudyprpject.repositories.PatientRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        patientService = new PatientService(patientRepository, passwordEncoder);
        patientService.setAppointmentService(appointmentService);
    }

    @Test
    void canFindById() {
        //given
        Long id = 1L;
        Patient testPatient = new Patient();
        testPatient.setName("test_name");
        given(patientRepository.findById(id))
                .willReturn(Optional.of(testPatient));
        //when
        Patient foundPatient = patientService.getById(id);
        //then
        verify(patientRepository).findById(id);
        assertThat(foundPatient.getName()).isEqualTo(testPatient.getName());
    }

    @Test
    void canNotFindByIdAndThrownAnException() {
        //given
        Long id = 1L;
        given(patientRepository.findById(id))
                .willReturn(Optional.empty());
//        Mockito.doReturn(Optional.empty())
//                .when(patientRepository).findById(id);
        //when
        //then
        assertThatThrownBy(() -> patientService.getById(id))
                .isInstanceOf(PatientNotFoundException.class)
                .hasMessage("patient with id " + id + " doesn't exist in base");
    }

    @Test
    void canDeleteById() {
        //given
        Long id = 1L;
        Patient deletingPatient = new Patient();
        given(patientRepository.findById(id))
                .willReturn(Optional.of(deletingPatient));
        //when
        patientService.deleteById(id);
        //then
        verify(patientRepository).delete(deletingPatient);
    }

    @Test
    void canNotDeleteByIdBecauseUserDoesNotExistInDB() {
        //given
        Long id = 1L;
        given(patientRepository.findById(id))
                .willReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> patientService.deleteById(id))
                .isInstanceOf(PatientNotFoundException.class)
                .hasMessage("patient with id " + id + " doesn't exist in base");
        verify(patientRepository, never()).delete(any());
    }

    @Test
    void canCreateNewPatient() {
        //given
        String patientPassword = "password";
        Patient patient = new Patient("name",
                "patronymic",
                "surname",
                "login",
                patientPassword,
                "number",
                new ArrayList<>(),
                "policy_number",
                new HashSet<>());
        ArgumentCaptor<Patient> patientArgumentCaptor = ArgumentCaptor.forClass(Patient.class);
        //when
        patientService.create(patient);
        //then
        verify(patientRepository).findByLogin(patient.getLogin());
        verify(patientRepository).findByPhoneNumber(patient.getPhoneNumber());
        verify(patientRepository).findByPolicyNumber(patient.getPolicyNumber());
        verify(passwordEncoder).encode(patientPassword);
        verify(patientRepository).save(patientArgumentCaptor.capture());
        assertThat(patientArgumentCaptor.getValue().getName()).isEqualTo(patient.getName());
    }

    @Test
    void canNotCreateNewPatientBecausePatientWIthSameSameLoginExistsInBase() {
        //given
        String patientPassword = "password";
        Patient patient = new Patient("name",
                "patronymic",
                "surname",
                "login",
                patientPassword,
                "number",
                new ArrayList<>(),
                "policy_number",
                new HashSet<>());
        given(patientRepository.findByLogin(patient.getLogin()))
                .willReturn(Optional.of(patient));
        //when
        //then
        assertThatThrownBy(() -> patientService.create(patient))
                .isInstanceOf(PatientDataValidationException.class)
                .hasMessage("patient with login "
                                + patient.getLogin() + " already exists");
        verify(patientRepository, never()).findByPhoneNumber(anyString());
        verify(patientRepository, never()).findByPolicyNumber(anyString());
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void canNotCreateNewPatientBecausePatientWIthSameSamePhoneNumberExistsInBase() {
        //given
        String patientPassword = "password";
        Patient patient = new Patient("name",
                "patronymic",
                "surname",
                "login",
                patientPassword,
                "number",
                new ArrayList<>(),
                "policy_number",
                new HashSet<>());
        given(patientRepository.findByPhoneNumber(patient.getPhoneNumber()))
                .willReturn(Optional.of(patient));
        //when
        //then
        assertThatThrownBy(() -> patientService.create(patient))
                .isInstanceOf(PatientDataValidationException.class)
                .hasMessage("patient with phone number "
                        + patient.getPhoneNumber() + " already exists");
        verify(patientRepository).findByLogin(anyString());
        verify(patientRepository, never()).findByPolicyNumber(anyString());
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void canNotCreateNewPatientBecausePatientWIthSameSamePolicyNumberExistsInBase() {
        //given
        String patientPassword = "password";
        Patient patient = new Patient("name",
                "patronymic",
                "surname",
                "login",
                patientPassword,
                "number",
                new ArrayList<>(),
                "policy_number",
                new HashSet<>());
        given(patientRepository.findByPolicyNumber(patient.getPolicyNumber()))
                .willReturn(Optional.of(patient));
        //when
        //then
        assertThatThrownBy(() -> patientService.create(patient))
                .isInstanceOf(PatientDataValidationException.class)
                .hasMessage("patient with policy number "
                        + patient.getPolicyNumber() + " already exists");
        verify(patientRepository).findByLogin(anyString());
        verify(patientRepository).findByPhoneNumber(anyString());
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void canChangePatientData() {
        //given
        String patientPassword = "password";
        Long id = 1L;
        Patient updatedPatient = new Patient("name",
                "patronymic",
                "surname",
                "login",
                patientPassword,
                "number",
                new ArrayList<>(),
                "policy_number",
                new HashSet<>());
        Patient previousPatient = new Patient("name",
                "patronymic",
                "surname",
                "login1",
                patientPassword,
                "number1",
                new ArrayList<>(),
                "policy_number1",
                new HashSet<>());
        updatedPatient.setId(id);
        ArgumentCaptor<Patient> argumentCaptor = ArgumentCaptor.forClass(Patient.class);
        given(patientRepository.findById(id))
                .willReturn(Optional.of(previousPatient));
        //when
        patientService.changePatientData(updatedPatient);
        //then
        verify(patientRepository).findById(updatedPatient.getId());
        verify(patientRepository).findByLogin(updatedPatient.getLogin());
        verify(patientRepository).findByPhoneNumber(updatedPatient.getPhoneNumber());
        verify(patientRepository).findByPolicyNumber(updatedPatient.getPolicyNumber());
        verify(patientRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getLogin()).isEqualTo(updatedPatient.getLogin());
    }

    @Test
    void canNotChangePatientDataBecauseNoSuchPatientInDB() {
        //given
        String patientPassword = "password";
        Long id = 1L;
        Patient updatedPatient = new Patient("name",
                "patronymic",
                "surname",
                "login",
                patientPassword,
                "number",
                new ArrayList<>(),
                "policy_number",
                new HashSet<>());
        updatedPatient.setId(id);
        given(patientRepository.findById(id))
                .willReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> patientService.changePatientData(updatedPatient))
                .isInstanceOf(PatientNotFoundException.class)
                .hasMessage("patient with id "
                        + updatedPatient.getId() +
                        " doesn't exist in base");
        verify(patientRepository, never()).findByLogin(anyString());
        verify(patientRepository, never()).findByPhoneNumber(anyString());
        verify(patientRepository, never()).findByPolicyNumber(anyString());
        verify(patientRepository, never()).save(any());
    }

    @Test
    void canNotChangePatientDataBecausePatientWithSameLoginExistsInDB() {
        //given
        String patientPassword = "password";
        Long id = 1L;
        Patient updatedPatient = new Patient("name",
                "patronymic",
                "surname",
                "login1",
                patientPassword,
                "number",
                new ArrayList<>(),
                "policy_number",
                new HashSet<>());
        Patient previousPatient = new Patient("name",
                "patronymic",
                "surname",
                "login",
                patientPassword,
                "number",
                new ArrayList<>(),
                "policy_number",
                new HashSet<>());
        updatedPatient.setId(id);

        given(patientRepository.findById(id))
                .willReturn(Optional.of(previousPatient));
        given(patientRepository.findByLogin(updatedPatient.getLogin()))
                .willReturn(Optional.of(new Patient()));
        //when
        //then
        assertThatThrownBy(() -> patientService.changePatientData(updatedPatient))
                .isInstanceOf(PatientDataValidationException.class)
                .hasMessage("patient with login "
                        + updatedPatient.getLogin() + " already exists");
        verify(patientRepository).findById(updatedPatient.getId());
        verify(patientRepository, never()).findByPhoneNumber(anyString());
        verify(patientRepository, never()).findByPolicyNumber(anyString());
        verify(patientRepository, never()).save(any());
    }

    @Test
    void canNotChangePatientDataBecausePatientWithSamePhoneNumberExistsInDB() {
        //given
        String patientPassword = "password";
        Long id = 1L;
        Patient updatedPatient = new Patient("name",
                "patronymic",
                "surname",
                "login",
                patientPassword,
                "number1",
                new ArrayList<>(),
                "policy_number",
                new HashSet<>());
        Patient previousPatient = new Patient("name",
                "patronymic",
                "surname",
                "login",
                patientPassword,
                "number",
                new ArrayList<>(),
                "policy_number",
                new HashSet<>());
        updatedPatient.setId(id);

        given(patientRepository.findById(id))
                .willReturn(Optional.of(previousPatient));
        given(patientRepository.findByPhoneNumber(updatedPatient.getPhoneNumber()))
                .willReturn(Optional.of(new Patient()));
        //when
        //then
        assertThatThrownBy(() -> patientService.changePatientData(updatedPatient))
                .isInstanceOf(PatientDataValidationException.class)
                .hasMessage("patient with phone number "
                        + updatedPatient.getPhoneNumber() + " already exists");
        verify(patientRepository).findById(updatedPatient.getId());
        verify(patientRepository, never()).findByLogin(anyString());
        verify(patientRepository, never()).findByPolicyNumber(anyString());
        verify(patientRepository, never()).save(any());
    }

    @Test
    void canNotChangePatientDataBecausePatientWithSamePolicyNumberExistsInDB() {
        //given
        String patientPassword = "password";
        Long id = 1L;
        Patient updatedPatient = new Patient("name",
                "patronymic",
                "surname",
                "login",
                patientPassword,
                "number",
                new ArrayList<>(),
                "policy_number1",
                new HashSet<>());
        Patient previousPatient = new Patient("name",
                "patronymic",
                "surname",
                "login",
                patientPassword,
                "number",
                new ArrayList<>(),
                "policy_number",
                new HashSet<>());
        updatedPatient.setId(id);

        given(patientRepository.findById(id))
                .willReturn(Optional.of(previousPatient));
        given(patientRepository.findByPolicyNumber(updatedPatient.getPolicyNumber()))
                .willReturn(Optional.of(new Patient()));
        //when
        //then
        assertThatThrownBy(() -> patientService.changePatientData(updatedPatient))
                .isInstanceOf(PatientDataValidationException.class)
                .hasMessage("patient with policy number "
                        + updatedPatient.getPolicyNumber() + " already exists");
        verify(patientRepository).findById(updatedPatient.getId());
        verify(patientRepository, never()).findByLogin(anyString());
        verify(patientRepository, never()).findByPhoneNumber(anyString());
        verify(patientRepository, never()).save(any());
    }

    @Test
    void canGetPatientsByDoctor() {
        //given
        Doctor doctor = new Doctor();
        doctor.setId(0L);
        //when
        patientService.getPatientsByDoctor(doctor);
        //then
        verify(patientRepository).findDistinctPatientsByDoctor(doctor);
    }

    @Test
    void canCreateAppointmentForPatient() {
        //given
        Appointment appointment = new Appointment();
        appointment.setId(0L);
        //when
        patientService.createAppointmentForPatient(appointment);
        //then
        verify(appointmentService).saveAppointment(appointment);

    }
}