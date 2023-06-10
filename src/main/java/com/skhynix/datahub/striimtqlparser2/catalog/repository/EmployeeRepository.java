package com.skhynix.datahub.striimtqlparser2.catalog.repository;

import com.skhynix.datahub.striimtqlparser2.catalog.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}