package com.bfhl.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Request Data Transfer Object for BFHL API
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {

    @NotNull(message = "Data field cannot be null")
    @NotEmpty(message = "Data array cannot be empty")
    private List<String> data;
}
