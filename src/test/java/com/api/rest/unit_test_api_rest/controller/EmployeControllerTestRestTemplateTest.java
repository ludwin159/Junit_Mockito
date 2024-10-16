package com.api.rest.unit_test_api_rest.controller;

import com.api.rest.unit_test_api_rest.models.Employe;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Indicar el orden de los elementos
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Para que levante nuestro proyecto en un puerto
public class EmployeControllerTestRestTemplateTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static Employe employe;

    private String path = "http://localhost:8080/api/employes";
    @BeforeAll
    static void beforeAll() {
        employe = Employe.builder()
                .id(1L)
                .name("Elizabeth")
                .lastName("Ingaroca Rosales")
                .email("eingaroca@hotmail.com")
                .build();
    }

    @DisplayName("RT guardar empleado")
    @Test
    @Order(1)
    void testSaveEmploye() {
        ResponseEntity<Employe> response = testRestTemplate.postForEntity(path, employe, Employe.class);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

        Employe employeCreated = response.getBody();
        Assertions.assertNotNull(employeCreated);
        Assertions.assertEquals(1L, employeCreated.getId());
        Assertions.assertEquals("Elizabeth", employeCreated.getName());
        Assertions.assertEquals("Ingaroca Rosales", employeCreated.getLastName());
        Assertions.assertEquals("eingaroca@hotmail.com", employeCreated.getEmail());
    }
}
