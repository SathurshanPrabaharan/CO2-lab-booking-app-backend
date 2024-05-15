package com.userservice.DTO.Response;


import com.userservice.Models.Profession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfessionListResponse {

    private String message;
    private Result data;


    public ProfessionListResponse(String message, List<Profession> foundedProfessions, int page, int size) {
        this.message = message;
        this.data = new Result(foundedProfessions,page,size);
    }

    public ProfessionListResponse(String message, List<Profession> foundedProfessions) {
        this.message = message;
        this.data = new Result(foundedProfessions);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class Result{
    private int total;
    private List<Profession> results;

    Result(List<Profession> results, Integer page, Integer size) {
        this.total = results.size();
        int startIndex = (page - 1) * size;
        int endIndex = Math.min(page * size, total);
        if (startIndex < total) {
            this.results = results.subList(startIndex, endIndex);
        } else {
            this.results = Collections.emptyList();
        }
    }

    Result(List<Profession> results) {
        this.total = results.size();
        this.results = results;
    }

}


