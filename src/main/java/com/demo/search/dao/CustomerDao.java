package com.demo.search.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class for Customer.
 */
@Entity
@Table(name = "Customer")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyDao company;
}
