package com.inventoryservice.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class InventoryNotFoundException extends RuntimeException{

    public InventoryNotFoundException(String message){
        super(message);
    }

    /**
     * Constructs a new InventoryNotFoundException with the specified detail message
     * and cause.

     * message: the detail message
     * cause: the cause (which is saved for later retrieval by the Throwable.getCause() method)
     */
    public InventoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}

