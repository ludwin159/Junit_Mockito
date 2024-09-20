package com.api.rest.unit_test_api_rest.controller;

import com.api.rest.unit_test_api_rest.models.Employe;
import com.api.rest.unit_test_api_rest.service.EmployeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static  org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;


// Probar controllers
// MockMVC para hacer peticiones hhtp
@WebMvcTest
class EmployeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeService service;
    @Autowired
    private ObjectMapper objectMapper;

    private Employe employe;
    private final String urlEmploye = "/api/employes";

    @BeforeEach
    void setUp() {
        employe = Employe.builder()
                .id(1L)
                .name("Elizabeth")
                .lastName("Ingaroca Rosales")
                .email("eingaroca@hotmail.com")
                .build();
    }

    @DisplayName("Guardar empleado")
    @Test
    void saveEmploye() throws Exception {
        // given
        given(service.saveEmploye(Mockito.any(Employe.class)))
                .willAnswer(invocation -> invocation.getArgument(0)); // Esto es para obtener el argumento 0
        // where
        ResultActions response = mockMvc.perform(post(urlEmploye)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employe))); // Convertir a json y mandar

        // then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(employe.getName())))
                .andExpect(jsonPath("$.lastName", is(employe.getLastName())))
                .andExpect(jsonPath("$.email", is(employe.getEmail())));
    }

    @DisplayName("Listar empleados")
    @Test
    void getAllEmployes() throws Exception {
        // Given
        List<Employe> employees = Arrays.asList(
                Employe.builder().id(1L).name("Elizabeth").lastName("Ingaroca Rosales").email("eingaroca@hotmail.com").build(),
                Employe.builder().id(2L).name("Carlos").lastName("Perez Diaz").email("cperez@empresa.com").build(),
                Employe.builder().id(3L).name("Maria").lastName("Gomez Fernandez").email("mgomez@gmail.com").build(),
                Employe.builder().id(4L).name("Luis").lastName("Suarez Ortega").email("lsuarez@correo.com").build(),
                Employe.builder().id(5L).name("Ana").lastName("Ramirez Lopez").email("aramirez@hotmail.com").build()
        );
        given(service.getAllEmploye()).willReturn(employees);

        // When
        ResultActions result = mockMvc.perform(get(urlEmploye));
        // Then
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(employees.size())));
    }

    @DisplayName("Obtener empleado por id y encuentra")
    @Test
    void getEmployeById() throws Exception {
        // given
        given(service.getEmployeById(employe.getId())).willReturn(Optional.of(employe));
        // when
        ResultActions response = mockMvc.perform(get(urlEmploye+"/{id}", employe.getId()));
        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(employe.getName())))
                .andExpect(jsonPath("$.lastName", is(employe.getLastName())))
                .andExpect(jsonPath("$.email", is(employe.getEmail())));
    }

    @DisplayName("Obtener empleado por id y NO encuentra")
    @Test
    void getEmployeByIdAndNotFound() throws Exception {
        // given
        given(service.getEmployeById(employe.getId())).willReturn(Optional.empty());
        // when
        ResultActions response = mockMvc.perform(get(urlEmploye+"/{id}", employe.getId()));
        // then
        response.andExpect(status().isNotFound()).andDo(print());
    }
    @DisplayName("Actualizar empleado encontrado")
    @Test
    void updateEmploye() throws Exception {
        Employe employeUpdated = Employe.builder().name("Ana").lastName("Ramirez Lopez").email("aramirez@hotmail.com").build();
        // given
        given(service.getEmployeById(employe.getId())).willReturn(Optional.of(employe));
        given(service.updateEmpoye(any(Employe.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // when
        ResultActions response = mockMvc.perform(put(urlEmploye+"/{id}", employe.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeUpdated)));
        // then
        response.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(employeUpdated.getName())))
                .andExpect(jsonPath("$.lastName", is(employeUpdated.getLastName())))
                .andExpect(jsonPath("$.email", is(employeUpdated.getEmail())));
    }
    @DisplayName("Actualizar empleado No encontrado")
    @Test
    void updateEmployeNotFound() throws Exception {
        Employe employeUpdated = Employe.builder().name("Ana").lastName("Ramirez Lopez").email("aramirez@hotmail.com").build();
        // given
        given(service.getEmployeById(employe.getId())).willReturn(Optional.empty());

        // when
        ResultActions response = mockMvc.perform(put(urlEmploye+"/{id}", employe.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeUpdated)));
        // then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("Eliminando empleado")
    @Test
    void deleteEmploye() throws Exception {
        // given
        willDoNothing().given(service).deleteEmployeById(employe.getId());
        // when
        ResultActions response = mockMvc.perform(delete(urlEmploye+"/{id}", employe.getId()));

        // then
        response.andExpect(status().isOk())
                .andDo(print());
    }
}