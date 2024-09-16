package com.api.rest.unit_test_api_rest.repository;

import com.api.rest.unit_test_api_rest.models.Employe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeRepositoryTest {
    @Autowired
    private EmployeRepository repository;

    private Employe employe;

    @BeforeEach
    void setUp() {
        employe = Employe.builder()
                .name("Elizabeth")
                .lastName("Ingaroca Rosales")
                .email("eingaroca@hotmail.com")
                .build();
    }

    @Test
    @DisplayName("Test Guardar Empleado")
    void saveEmployeTest() {
        // Given - dado
        Employe employe = Employe.builder()
                .name("Ludwin")
                .email("lsuarez@integrens.com")
                .lastName("Suárez Ingaroca").build();
        // When - Cuando hagamos algo
        Employe employeSaved = repository.save(employe);

        // Then - Verificar
        Assertions.assertThat(employeSaved).isNotNull();
        Assertions.assertThat(employeSaved.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Listar todos los empleados")
    void getALlEmployes() {
        List<Employe> allEmployes = repository.findAll();
        Assertions.assertThat(allEmployes).isNotNull();
    }

    @Test
    @DisplayName("Listar todos los empleados 1")
    void getALlEmployes1() {
        // Metodología BDD
        // Given
        Employe employe1 = Employe.builder()
                .name("Wilder")
                .lastName("Ramirez Valdeon")
                .email("w2@hotmail.com")
                .build();
        repository.save(employe);
        repository.save(employe1);

        // When
        List<Employe> allEmployes = repository.findAll();

        // Then
        Assertions.assertThat(allEmployes).isNotNull();
        Assertions.assertThat(allEmployes.size()).isEqualTo(2);


    }

    @DisplayName("Test Para obtener un empleado por id")
    @Test
    void getEmployeByIdTest() {
        // Given
        repository.save(employe);

        // When
        Employe employedb = repository.findById(employe.getId()).orElse(null);

        // Then
        Assertions.assertThat(employedb).isNotNull();
    }

    @DisplayName("Test para actualizar empleado")
    @Test
    void updateEmploye() {
        repository.save(employe);

        // When
        Employe employe1 = repository.findById(employe.getId()).orElse(null);
        if (employe1 != null) {
            employe1.setName("Rushian");
            employe1.setLastName("Izarra Santivañes");
            employe1.setEmail("rsanti@gmail.com");
            Employe employeUpdated = repository.save(employe1);

            // Then
            Assertions.assertThat(employeUpdated.getEmail()).isEqualTo("rsanti@gmail.com");
            Assertions.assertThat(employeUpdated.getName()).isEqualTo("Rushian");
            Assertions.assertThat(employeUpdated.getLastName()).isEqualTo("Izarra Santivañes");
        }

    }

    @DisplayName("Eliminar Empleado")
    @Test
    void deleteEmployeTest() {
        repository.save(employe);

        // when
        repository.deleteById(employe.getId());
        Employe employe1 = repository.findById(employe.getId()).orElse(null);
        // then
        Assertions.assertThat(employe1).isNull();


    }
}