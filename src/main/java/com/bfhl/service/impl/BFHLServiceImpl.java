package com.bfhl.service.impl;

import com.bfhl.dto.RequestDTO;
import com.bfhl.dto.ResponseDTO;
import com.bfhl.exception.BfhlException;
import com.bfhl.service.BFHLService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service implementation for BFHL operations
 */
@Slf4j
@Service
public class BFHLServiceImpl implements BFHLService {

    // Service constants
    private static final String USER_ID = "channpreet_kaur_17062004";
    private static final String EMAIL = "channpreet0401.be23@chitkara.edu.in";
    private static final String ROLL_NUMBER = "2310990401";

    /**
     * Process BFHL request and return processed response
     *
     * @param request the request containing data array
     * @return the processed response with categorized data
     */
    @Override
    public ResponseDTO processRequest(RequestDTO request) {
        try {
            if (request == null || request.getData() == null || request.getData().isEmpty()) {
                throw new BfhlException("Request data cannot be null or empty", "INVALID_INPUT");
            }

            List<String> data = request.getData();

            // Process data
            List<String> oddNumbers = new ArrayList<>();
            List<String> evenNumbers = new ArrayList<>();
            List<String> alphabets = new ArrayList<>();
            List<String> specialCharacters = new ArrayList<>();
            long sum = 0;

            // Categorize data
            for (String item : data) {
                if (item == null || item.trim().isEmpty()) {
                    continue;
                }

                String trimmedItem = item.trim();

                if (isNumeric(trimmedItem)) {
                    long number = Long.parseLong(trimmedItem);
                    sum += number;

                    if (number % 2 == 0) {
                        evenNumbers.add(trimmedItem);
                    } else {
                        oddNumbers.add(trimmedItem);
                    }
                } else if (isAlphabetic(trimmedItem)) {
                    // Extract individual characters from alphabetic string
                    for (char c : trimmedItem.toUpperCase().toCharArray()) {
                        alphabets.add(String.valueOf(c));
                    }
                } else {
                    specialCharacters.add(trimmedItem);
                }
            }

            // Generate concat string
            String concatString = generateConcatString(alphabets);

            // Build response
            ResponseDTO response = ResponseDTO.builder()
                    .isSuccess(true)
                    .userId(USER_ID)
                    .email(EMAIL)
                    .rollNumber(ROLL_NUMBER)
                    .oddNumbers(oddNumbers)
                    .evenNumbers(evenNumbers)
                    .alphabets(alphabets)
                    .specialCharacters(specialCharacters)
                    .sum(String.valueOf(sum))
                    .concatString(concatString)
                    .build();

            log.info("Request processed successfully");
            return response;

        } catch (BfhlException ex) {
            log.error("BFHL processing error: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Unexpected error during request processing", ex);
            throw new BfhlException("Error processing request: " + ex.getMessage(), "PROCESSING_ERROR");
        }
    }

    /**
     * Check if string is numeric
     *
     * @param str the string to check
     * @return true if numeric, false otherwise
     */
    private boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Check if string is alphabetic
     *
     * @param str the string to check
     * @return true if alphabetic, false otherwise
     */
    private boolean isAlphabetic(String str) {
        return str.matches("[a-zA-Z]+");
    }

    /**
     * Generate concat string according to requirements:
     * 1. Take all alphabetical characters
     * 2. Concatenate them
     * 3. Reverse the string
     * 4. Apply alternating capitalization
     *
     * @param alphabets list of alphabetic values
     * @return processed concat string
     */
    private String generateConcatString(List<String> alphabets) {
        if (alphabets == null || alphabets.isEmpty()) {
            return "";
        }

        // Concatenate all alphabets
        String concatenated = alphabets.stream()
                .collect(Collectors.joining());

        // Reverse the string
        String reversed = new StringBuilder(concatenated).reverse().toString();

        // Apply alternating capitalization
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < reversed.length(); i++) {
            char ch = reversed.charAt(i);
            if (i % 2 == 0) {
                result.append(Character.toUpperCase(ch));
            } else {
                result.append(Character.toLowerCase(ch));
            }
        }

        return result.toString();
    }
}
