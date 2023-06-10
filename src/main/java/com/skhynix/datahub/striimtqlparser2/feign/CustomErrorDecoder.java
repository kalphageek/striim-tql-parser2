package com.skhynix.datahub.striimtqlparser2.feign;

import com.skhynix.datahub.striimtqlparser2.common.BadRequestException;
import com.skhynix.datahub.striimtqlparser2.common.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder  implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        switch (response.status()){
            case 400:
                return new BadRequestException();
            case 404:
                return new NotFoundException();
            default:
                return new Exception("Generic error");
        }
    }
}