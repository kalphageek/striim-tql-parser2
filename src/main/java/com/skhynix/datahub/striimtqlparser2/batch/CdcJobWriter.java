package com.skhynix.datahub.striimtqlparser2.batch;

import com.skhynix.datahub.striimtqlparser2.catalog.entity.CdcJobEntity;
import com.skhynix.datahub.striimtqlparser2.catalog.entity.CdcJobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CdcJobWriter implements ItemWriter<CdcJobEntity> {
    private final CdcJobRepository cdcJobRepository;

    @Override
    public void write(List<? extends CdcJobEntity> items) throws Exception {
        cdcJobRepository.saveAll(items);
    }
}
