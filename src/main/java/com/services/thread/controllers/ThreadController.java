package com.services.thread.controllers;

import com.services.thread.domain.dtos.RequestObjectDTO;
import com.services.thread.executor.ServiceExecutor;
import com.services.thread.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/thread")
public class ThreadController {

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private RequestService requestService;


    @PostMapping("/start")
    public ResponseEntity<String> processar(@RequestBody RequestObjectDTO requestObjectDTO) {
        try {
            executorService.submit(new ServiceExecutor(requestObjectDTO, requestService));
            return ResponseEntity.accepted().body("Requisição adicionada à fila");
        } catch (RejectedExecutionException e) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("Fila cheia. Tente novamente mais tarde.");
        }
    }
}