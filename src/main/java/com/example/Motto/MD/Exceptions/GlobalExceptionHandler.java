package com.example.Motto.MD.Exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException e) {
        Map<String, Object> errorsReturn = new HashMap<>();
        List<String> errorList = new ArrayList<>();
        errorList.add(e.getMessage());
        errorsReturn.put("Reasons: ", errorList);
        return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(errorsReturn);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, Object> errorsReturn = new HashMap<>();
        List<String> errorList = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(error -> errorList.add(error.getDefaultMessage()));
        errorsReturn.put("Reasons: ", errorList);
        return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(errorsReturn);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    private ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(Map.of("Error: ", "Invalid JSON Structure."));
    }

    @ExceptionHandler(StorageException.class)
    protected ResponseEntity<Map<String, Object>> handleStorageException(StorageException e) {
        Map<String, Object> errorsReturn = new HashMap<>();
        List<String> errorList = new ArrayList<>();
        errorList.add(e.getMessage());
        errorsReturn.put("Reasons: ", errorList);
        return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(errorsReturn);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(HttpStatusCode.valueOf(405)).body(e.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    protected ResponseEntity<String> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        return ResponseEntity.status(HttpStatusCode.valueOf(413)).body(e.getMessage());
    }

}
