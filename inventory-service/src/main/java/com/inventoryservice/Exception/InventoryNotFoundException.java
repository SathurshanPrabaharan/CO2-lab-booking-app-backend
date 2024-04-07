package com.inventoryservice.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)

public class InventoryNotFoundException extends Exception{

    public InventoryNotFoundException(String message){
        super(message);
    }



}
