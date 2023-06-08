package com.skhynix.datahub.striimtqlparser2.batch;

import com.skhynix.datahub.striimtqlparser2.catalog.entity.CdcJobEntity;
import com.skhynix.datahub.striimtqlparser2.dto.RESTResponese;
import com.skhynix.datahub.striimtqlparser2.service.proxy.StriimApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
@Component
public class CdcJobReader implements ItemReader<CdcJobEntity> {
    private final StriimApiClient striimApiClient;
    private boolean dataFetched = false;
    private Iterator<CdcJobEntity> cdcJobEntityIterator;
    @Override
    public CdcJobEntity read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (!dataFetched) {
            RESTResponese srcTgtTables = striimApiClient.getSrcTgtTables();
            List<CdcJobEntity> cdcJobEntities = new ArrayList<>(); //srcTgtTables.getCdcJobEntites;
            dataFetched = true;
        }

        if (cdcJobEntityIterator.hasNext()) {
            return cdcJobEntityIterator.next();
        } else {
            return null;
        }
    }

}
