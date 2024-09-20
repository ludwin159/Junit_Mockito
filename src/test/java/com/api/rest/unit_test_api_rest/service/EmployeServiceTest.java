package com.api.rest.unit_test_api_rest.service;

import com.api.rest.unit_test_api_rest.exception.ResourceNotFoundException;
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
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
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
                .id(1L)
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

    @DisplayName("Test para guardar empleado con Excepcion")
    @Test
    void saveEmployeWithException() {
        // Given
        BDDMockito.given(employeRepository.findByEmail(employe.getEmail()))
                .willReturn(Optional.of(employe));
        //When
        // Con esto estamos capturando la excepción cuando se ejecuta el save, ya que si la tendremos por que estamos simulando que ya tenemos un employe en la bd.
        assertThrows(ResourceNotFoundException.class,() -> employeService.saveEmploye(employe));
        //Then
        // COn esto verificamos que el repository nunca ejeuto el metodo save con ningún Employe
        verify(employeRepository, never()).save(any(Employe.class));
    }

    @DisplayName("Test para listar Empleados")
    @Test
    void getAllEmploye() {
        // Given
        Employe employe1 = Employe.builder()
                .id(2L)
                .name("Lucas")
                .lastName("Cabrera Rodriguez")
                .email("lcabrera@gmail.com").build();
        BDDMockito.given(employeRepository.findAll()).willReturn(List.of(employe1, employe));

        // When
        List<Employe> employes = employeService.getAllEmploye();

        // Then
        Assertions.assertThat(employes).isNotNull();
        Assertions.assertThat(employes.size()).isEqualTo(2);
    }

    @DisplayName("Test listar una lista vacía")
    @Test
    void getAllEmployeEmptyList() {
        // Given
        List<Employe> employesEmpty = new ArrayList<>();
        BDDMockito.given(employeRepository.findAll()).willReturn(employesEmpty);

        // When
        List<Employe> employes = employeService.getAllEmploye();

        // Then
        Assertions.assertThat(employes).isNotNull();
        Assertions.assertThat(employes.size()).isEqualTo(0);
    }

    @DisplayName("Buscar y encontrar empleado por id")
    @Test
    void getEmployeById() {
        // Given
        BDDMockito.given(employeRepository.findById(employe.getId())).willReturn(Optional.of(employe));

        // When
        Employe employe1 = employeService.getEmployeById(employe.getId()).get();
        // Then
        Assertions.assertThat(employe1).isNotNull();
    }

    @DisplayName("Actualizar un empleado")
    @Test
    void updateEmpoye() {
        String newName = "Luchito";
        String newEmail = "lsuarez@ingrens";
        // Given
        BDDMockito.given(employeRepository.save(employe)).willReturn(employe);
        employe.setEmail(newEmail);
        employe.setName(newName);

        // When
        Employe employe1 = employeService.updateEmpoye(employe);

        // Then
        Assertions.assertThat(employe1.getName()).isEqualTo(newName);
        Assertions.assertThat(employe1.getEmail()).isEqualTo(newEmail);
        verify(employeRepository).save(employe);
    }

    @DisplayName("Eliminar Empleado")
    @Test
    void deleteEmployeById() {
        // given
        BDDMockito.given(employeRepository.findById(employe.getId())).willReturn(Optional.empty());
        BDDMockito.given(employeRepository.save(employe)).willReturn(employe);
        // when
        Employe employe1 = employeService.saveEmploye(employe);
        employeService.deleteEmployeById(employe1.getId());
        Optional<Employe> employeDeleted = employeService.getEmployeById(employe1.getId());
        // then
        Assertions.assertThat(employeDeleted).isEmpty();
    }

    @DisplayName("Eliminar empleado con metodo delete")
    @Test
    void deleteEmployeByIdInMethod() {
        // given
        Long employeId = 1L;
        BDDMockito.willDoNothing().given(employeRepository).deleteById(employeId);
        // when
        employeService.deleteEmployeById(employeId);
        // then
        verify(employeRepository, times(1)).deleteById(employeId);
    }
}