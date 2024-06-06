package com.userservice.DTO.Response.Staff;

import com.userservice.Models.Staff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StaffListResponse {

    private String message;
    private ResultStaff data;


    public StaffListResponse(String message, Page<Staff> filteredStaffs) {
        this.message = message;
        this.data = new ResultStaff(filteredStaffs);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResultStaff {
    private long total;
    private List<ModifiedStaff> results;

    ResultStaff(Page<Staff> results) {
        this.total = results.getTotalElements();

        List<ModifiedStaff> resultModifiedStaffs = new ArrayList<>();
        for(Staff currentStaff: results.getContent()){
            ModifiedStaff temp = new ModifiedStaff(currentStaff);
            resultModifiedStaffs.add(temp);
        }

        this.results = resultModifiedStaffs;

    }

}

