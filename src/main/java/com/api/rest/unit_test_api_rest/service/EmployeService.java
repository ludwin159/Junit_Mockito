package com.api.rest.unit_test_api_rest.service;

import com.api.rest.unit_test_api_rest.models.Employe;

import java.util.List;
import java.util.Optional;

public interface EmployeService {
    Employe saveEmploye(Employe employe);
    List<Employe> getAllEmploye();
    Optional<Employe> getEmployeById(Long id);
    Employe updateEmpoye(Employe newEmploye);
    void deleteEmployeById(Long id);
}
