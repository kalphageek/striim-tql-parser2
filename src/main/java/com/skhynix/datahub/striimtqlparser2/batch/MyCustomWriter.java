package com.skhynix.datahub.striimtqlparser2.batch;

import java.util.List;

import com.skhynix.datahub.striimtqlparser2.secondary.entity.Manager;
import com.skhynix.datahub.striimtqlparser2.secondary.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class MyCustomWriter implements ItemWriter<Manager> {

    private final ManagerRepository managerRepository ;

    @Override
    public void write(List<? extends Manager> list) throws Exception {
        for (Manager data : list) {
            System.out.println("MyCustomWriter    : Writing data    : " + data.getId()+" : "+data.getName()+" : "+data.getSalary());
            managerRepository.save(data);
        }
    }
}