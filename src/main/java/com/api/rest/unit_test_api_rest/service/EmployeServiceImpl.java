package com.api.rest.unit_test_api_rest.service;

import com.api.rest.unit_test_api_rest.exception.ResourceNotFoundException;
import com.api.rest.unit_test_api_rest.models.Employe;
import com.api.rest.unit_test_api_rest.repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class EmployeServiceImpl implements EmployeService{

    @Autowired
    private EmployeRepository employeRepository;
    @Override
    public Employe saveEmploye(Employe employe) {
        Optional<Employe> existEmploye = employeRepository.findByEmail(employe.getEmail());
        if (existEmploye.isPresent()) {
            throw new ResourceNotFoundException("The employe with that email already to exist!" + employe.getEmail());
        }
        return employeRepository.save(employe);
    }

    @Override
    public List<Employe> getAllEmploye() {
        return employeRepository.findAll();
    }

    @Override
    public Optional<Employe> getEmployeById(Long id) {
        return employeRepository.findById(id);
    }

    @Override
    public Employe updateEmpoye(Employe newEmploye) {
        return employeRepository.save(newEmploye);
    }

    @Override
    public void deleteEmployeById(Long id) {
        employeRepository.deleteById(id);
    }
}
