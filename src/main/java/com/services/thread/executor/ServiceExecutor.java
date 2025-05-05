package com.services.thread.executor;

import com.services.thread.domain.dtos.RequestObjectDTO;
import com.services.thread.service.RequestService;

public class ServiceExecutor implements Runnable{

    private final RequestObjectDTO requestObjectDTO;
    private final RequestService requestService;

    public ServiceExecutor(RequestObjectDTO requestObjectDTO, RequestService requestService){
        this.requestObjectDTO = requestObjectDTO;
        this.requestService = requestService;
    }

    @Override
    public void run(){
        requestService.start(requestObjectDTO);
    }
}
