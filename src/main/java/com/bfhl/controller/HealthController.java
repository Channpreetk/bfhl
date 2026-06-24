package com.bfhl.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Global Health Controller for root API health check
 */
@Slf4j
@RestController
public class HealthController {

    /**
     * GET endpoint for root API health check
     *
     * @return health status
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "message", "BFHL API is running"));
    }
}
