package com.skhynix.datahub.striimtqlparser2.secondary.repository;

import com.skhynix.datahub.striimtqlparser2.secondary.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

}