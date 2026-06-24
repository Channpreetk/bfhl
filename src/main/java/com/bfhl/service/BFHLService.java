package com.bfhl.service;

import com.bfhl.dto.RequestDTO;
import com.bfhl.dto.ResponseDTO;

/**
 * Service interface for BFHL operations
 */
public interface BFHLService {

    /**
     * Process BFHL request
     *
     * @param request the request containing data array
     * @return the processed response
     */
    ResponseDTO processRequest(RequestDTO request);
}
