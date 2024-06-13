package com.inventoryservice.DTO.Response.Software;


import com.inventoryservice.Models.Software;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SoftwareListResponse {
    private String message;
    private ResultSoftware data;

    public SoftwareListResponse(String message, Page<Software> filteredSoftware) {
        this.message = message;
        this.data = new ResultSoftware(filteredSoftware);
    }
}
@AllArgsConstructor
@NoArgsConstructor
@Data
class ResultSoftware{
    private long total;
    private List<ModifiedSoftware> results;

    public ResultSoftware(Page<Software> results) {
        this.total = results.getTotalElements();
        List<ModifiedSoftware> resultModifiedSoftwares = new ArrayList<>();
        for(Software currentSoftware : results.getContent()){
            ModifiedSoftware temp = new ModifiedSoftware(currentSoftware);
            resultModifiedSoftwares.add(temp);
        }
        this.results=resultModifiedSoftwares;
    }

}
