package br.com.ifba.gestaofinanceira.Infraestructure.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiError {

    private LocalDateTime timesTamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
    private String stackTrace;



}
