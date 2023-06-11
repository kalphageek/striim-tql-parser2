package com.skhynix.datahub.striimtqlparser2.dto;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true)
public class CsvData {
    Integer id;
    String name;
    Integer age;
}
