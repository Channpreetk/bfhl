package com.bfhl.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Error response structure
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

    @JsonProperty("is_success")
    private Boolean isSuccess;

    private String message;

    @JsonProperty("error_code")
    private String errorCode;

    private LocalDateTime timestamp;
}
