package com.skhynix.datahub.striimtqlparser2.feign.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "striim-api", url = "http://localhost:9561")
public interface StriimApiClient {
    @GetMapping("/api/{appName}/status")
    String getStatus(@PathVariable("appName") String appName);
    @GetMapping("/api/{appName}/describe")
    String getDescribe(@PathVariable("appName") String appName);
    @PostMapping("/api/export-tql")
    String exportTql();
}