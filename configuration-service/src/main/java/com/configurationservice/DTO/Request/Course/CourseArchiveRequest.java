package com.configurationservice.DTO.Request.Course;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseArchiveRequest {

    @NotNull(message = "Invalid updatedBy: updatedBy cannot be null")
    private UUID updatedBy;


}
