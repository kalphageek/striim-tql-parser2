package com.skhynix.datahub.striimtqlparser2.service.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "striim-api", url = "placeholder")
public interface StriimApiClient {
    @GetMapping("/{appName}/status")
    String getStatus(@PathVariable("appName") String appName);
}