package com.inventoryservice.ExceptionHandeler;
import com.inventoryservice.Exception.InventoryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class InventoryExceptionHandeler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InventoryNotFoundException.class)
    public Map<String, String> handleBusinessException(InventoryNotFoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }

}
