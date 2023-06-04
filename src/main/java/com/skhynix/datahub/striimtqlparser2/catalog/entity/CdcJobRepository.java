package com.skhynix.datahub.striimtqlparser2.catalog.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CdcJobRepository extends JpaRepository<CdcJobEntity, CdcJobEntity> {
}
