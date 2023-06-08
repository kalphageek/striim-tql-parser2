package com.skhynix.datahub.striimtqlparser2.service.proxy;

import com.skhynix.datahub.striimtqlparser2.catalog.entity.Persons;
import com.skhynix.datahub.striimtqlparser2.dto.RESTResponese;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "striim-api", url = "http://localhost:8080")
public interface StriimApiClient {

    @GetMapping("/srcTgtTables")
    public List<Persons> getPersons();
}
