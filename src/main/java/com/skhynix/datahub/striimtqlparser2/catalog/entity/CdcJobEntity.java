package com.skhynix.datahub.striimtqlparser2.catalog.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CdcJobEntity {
    @Id
    private String pk;
}
