package com.api.rest.unit_test_api_rest.repository;

import com.api.rest.unit_test_api_rest.models.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeRepository extends JpaRepository<Employe, Long> {

    Optional<Employe> findByEmail(String email);
}
