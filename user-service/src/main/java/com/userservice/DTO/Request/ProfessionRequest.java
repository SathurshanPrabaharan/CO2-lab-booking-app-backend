package com.userservice.DTO.Request;

import com.userservice.Enums.STATUS;
import com.userservice.Validations.ValidStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessionRequest {

    @NotEmpty(message = "Invalid Name: Name cannot be empty")
    private String name;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    @NotNull(message = "Invalid created_by: Created by cannot be null")
    @Positive(message = "Invalid created_by: Created by must be positive")
    private Long created_by;

    @NotNull(message = "Invalid status: Status cannot be null")
    @ValidStatus(message = "Invalid status: Status must be one of ACTIVE, INACTIVE, or ARCHIVED")
    private String status;

}
