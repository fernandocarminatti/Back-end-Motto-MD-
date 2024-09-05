package com.example.Motto.MD.Exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, Object> errorsReturn = new HashMap<>();
        List<String> errorList = new ArrayList<>();
        errorsReturn.put("Error:", "Could not parse data.");
        e.getBindingResult().getAllErrors().forEach(error -> errorList.add(error.getDefaultMessage()));
        errorsReturn.put("Reasons: ", errorList);

        return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(errorsReturn);
    }


}
