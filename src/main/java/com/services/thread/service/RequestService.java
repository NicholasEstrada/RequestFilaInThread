package com.services.thread.service;

import com.services.thread.domain.dtos.RequestObjectDTO;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
public class RequestService {

    public void start(RequestObjectDTO requestObjectDTO) {
        try{
            Instant execucaoIniciada = java.time.Instant.now();
            System.out.println("IN EXECUCAO: " + execucaoIniciada
                               + "\nRequestObjectDTO: " + requestObjectDTO.toString());
            Thread.sleep(10000);
            System.out.println("OUT EXECUCAO: " + execucaoIniciada);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
