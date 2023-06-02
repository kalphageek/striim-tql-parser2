package com.skhynix.datahub.striimtqlparser2.service.proxy;

import com.skhynix.datahub.striimtqlparser2.dto.RESTResponese;

public interface StriimServiceProxy {
    public RESTResponese exportTqlFiles();
    public RESTResponese getSrcTgtTables();
}
