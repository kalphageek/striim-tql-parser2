package com.skhynix.datahub.striimtqlparser2.catalog.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class Persons {
    @Id
    Integer id;
    String name;
}