package com.api.rest.unit_test_api_rest.controller;

import com.api.rest.unit_test_api_rest.models.Employe;
import com.api.rest.unit_test_api_rest.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
public class EmployeController {
    @Autowired
    private EmployeService employeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employe saveEmploye(@RequestBody Employe employe) {
        return employeService.saveEmploye(employe);
    }

    @GetMapping
    public List<Employe> getAllEmployes() {
        return employeService.getAllEmploye();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employe> getEmployeById(@PathVariable("id") Long employeId) {
        return employeService.getEmployeById(employeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employe> updateEmploye(@PathVariable("id") Long oldEmployeId, @RequestBody Employe newEmploye) {
        return employeService.getEmployeById(oldEmployeId)
                .map(dbEmploye -> {
                    dbEmploye.setName(newEmploye.getName());
                    dbEmploye.setLastName(newEmploye.getLastName());
                    dbEmploye.setEmail(newEmploye.getEmail());
                    Employe employeUpdated = employeService.updateEmpoye(dbEmploye);
                    return new ResponseEntity<>(employeUpdated, HttpStatus.CREATED);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmploye(@PathVariable Long id) {
        employeService.deleteEmployeById(id);
        return new ResponseEntity<>("Employe deleted!", HttpStatus.OK);
    }
}
