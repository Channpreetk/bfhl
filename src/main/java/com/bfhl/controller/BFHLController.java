package com.bfhl.controller;

import com.bfhl.dto.RequestDTO;
import com.bfhl.dto.ResponseDTO;
import com.bfhl.service.BFHLService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST Controller for BFHL API endpoints
 */
@Slf4j
@RestController
@RequestMapping("/bfhl")
public class BFHLController {

    private final BFHLService bfhlService;

    /**
     * Constructor injection for BFHLService
     *
     * @param bfhlService the BFHL service
     */
    public BFHLController(BFHLService bfhlService) {
        this.bfhlService = bfhlService;
    }

    /**
     * GET endpoint for health check
     *
     * @return health status
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "message", "BFHL API is running"));
    }

    /**
     * POST endpoint to process BFHL data
     *
     * @param request the request containing data array
     * @return response with processed data
     */
    @PostMapping
    public ResponseEntity<ResponseDTO> processBFHLData(@Valid @RequestBody RequestDTO request) {
        log.info("Received BFHL request with data: {}", request.getData());

        ResponseDTO response = bfhlService.processRequest(request);

        log.info("BFHL request processed successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
