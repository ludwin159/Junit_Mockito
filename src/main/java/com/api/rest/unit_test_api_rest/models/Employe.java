package com.api.rest.unit_test_api_rest.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employes")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email", nullable = false)
    private String email;
}
