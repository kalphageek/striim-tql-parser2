package com.skhynix.datahub.striimtqlparser2.batch.backup;

import com.skhynix.datahub.striimtqlparser2.catalog.entity.Employee;
import com.skhynix.datahub.striimtqlparser2.common.Constants;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class CustomEntityManagerReader extends JpaPagingItemReader<Employee> {
    public CustomEntityManagerReader(@Qualifier(Constants.CatalogEntityManager)EntityManagerFactory entityManagerFactory) {
        this.setEntityManagerFactory(entityManagerFactory);
        this.setQueryString("select m from Employee m");
        this.setPageSize(10);
    }
}
