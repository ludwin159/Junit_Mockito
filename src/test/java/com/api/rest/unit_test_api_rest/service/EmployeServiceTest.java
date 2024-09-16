package com.api.rest.unit_test_api_rest.service;

import com.api.rest.unit_test_api_rest.models.Employe;
import com.api.rest.unit_test_api_rest.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


@ExtendWith(MockitoExtension.class) // it is for charge some Mockito extentions
class EmployeServiceTest {

    @Mock
    private EmployeRepository employeRepository;

    @InjectMocks
    private EmployeServiceImpl employeService;
    private static Employe employe;


    @BeforeAll
    static void beforeAll() {
        employe = Employe.builder()
                .name("Matias")
                .lastName("Salas Rodriguez")
                .email("timatito@gmail.com").build();
    }

    @DisplayName("Test para guardar empleado")
    @Test
    void saveEmploye() {
        // Given
        // Aquí estamos simulando los métodos que ejecutaremos dentro del servicio
        BDDMockito.given(employeRepository.findByEmail(employe.getEmail()))
                .willReturn(Optional.empty());
        
        BDDMockito.given(employeRepository.save(employe)).willReturn(employe);

        //When
        Employe employeSaved = employeService.saveEmploye(employe);

        //Then
        Assertions.assertThat(employeSaved).isNotNull();
    }

    @Test
    void getAllEmploye() {
    }

    @Test
    void getEmployeById() {
    }

    @Test
    void updateEmpoye() {
    }

    @Test
    void deleteEmployeById() {
    }
}