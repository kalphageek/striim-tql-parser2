package com.skhynix.datahub.striimtqlparser2.batch;

import com.skhynix.datahub.striimtqlparser2.catalog.entity.Employee;
import com.skhynix.datahub.striimtqlparser2.common.Constants;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class MyCustomReader2 extends JpaPagingItemReader<Employee> {
    public MyCustomReader2(@Qualifier(Constants.CatalogEntityManager)EntityManagerFactory entityManagerFactory) {
        this.setEntityManagerFactory(entityManagerFactory);
        this.setQueryString("select m from Employee m");
        this.setPageSize(10);
    }
}
