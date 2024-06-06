package com.userservice.DTO.Response.Profession;


import com.userservice.Models.SupportModels.Profession;
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
    private ResultProfession data;


    public ProfessionListResponse(String message, List<Profession> foundedProfessions, int page, int size) {
        this.message = message;
        this.data = new ResultProfession(foundedProfessions,page,size);
    }

    public ProfessionListResponse(String message, List<Profession> foundedProfessions) {
        this.message = message;
        this.data = new ResultProfession(foundedProfessions);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResultProfession{
    private int total;
    private List<Profession> results;

    ResultProfession(List<Profession> results, Integer page, Integer size) {
        this.total = results.size();
        int startIndex = (page - 1) * size;
        int endIndex = Math.min(page * size, total);
        if (startIndex < total) {
            this.results = results.subList(startIndex, endIndex);
        } else {
            this.results = Collections.emptyList();
        }
    }

    ResultProfession(List<Profession> results) {
        this.total = results.size();
        this.results = results;
    }

}


