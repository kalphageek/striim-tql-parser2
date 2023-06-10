package com.skhynix.datahub.striimtqlparser2.batch;

import com.skhynix.datahub.striimtqlparser2.catalog.entity.Employee;
import com.skhynix.datahub.striimtqlparser2.secondary.entity.Manager;
import com.skhynix.datahub.striimtqlparser2.feign.proxy.StriimApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
@Slf4j
public class MyCustomProcessor implements ItemProcessor<Employee, Manager> {

    private final StriimApiClient striimApiClient;

    @Override
    public Manager process(Employee emp) throws Exception {
        System.out.println("MyBatchProcessor : Processing data : "+emp);
        Manager manager = new Manager();
        //manager.setId(emp.getId());
        manager.setName(emp.getName().toUpperCase());
        manager.setSalary(emp.getSalary());

        String appName = "aa";
        String result = striimApiClient.getStatus(appName);
        log.info("# striimApiClient.getStatus({}) : {}", appName, result);

        return manager;
    }
}