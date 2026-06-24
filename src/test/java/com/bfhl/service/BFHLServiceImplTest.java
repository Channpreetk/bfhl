package com.bfhl.service;

import com.bfhl.dto.RequestDTO;
import com.bfhl.dto.ResponseDTO;
import com.bfhl.exception.BfhlException;
import com.bfhl.service.impl.BFHLServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for BFHLServiceImpl
 * Test coverage for:
 * - Mixed input (numbers, alphabets, special characters)
 * - Only alphabets
 * - Only numbers
 * - Empty array
 * - Special characters
 */
@DisplayName("BFHL Service Tests")
class BFHLServiceImplTest {

    private BFHLService bfhlService;

    @BeforeEach
    void setUp() {
        bfhlService = new BFHLServiceImpl();
    }

    /**
     * Test 1: Mixed input scenario
     * Input: ["a","1","334","4","R","$"]
     */
    @Test
    @DisplayName("Test mixed input with numbers, alphabets and special characters")
    void testMixedInput() {
        // Arrange
        RequestDTO request = new RequestDTO(Arrays.asList("a", "1", "334", "4", "R", "$"));

        // Act
        ResponseDTO response = bfhlService.processRequest(request);

        // Assert
        assertTrue(response.getIsSuccess());
        assertEquals("channpreet_kaur_17062004", response.getUserId());
        assertEquals("channpreet0401.be23@chitkara.edu.in", response.getEmail());
        assertEquals("2310990401", response.getRollNumber());

        // Verify odd numbers
        assertTrue(response.getOddNumbers().contains("1"));
        assertEquals(1, response.getOddNumbers().size());

        // Verify even numbers
        assertTrue(response.getEvenNumbers().contains("334"));
        assertTrue(response.getEvenNumbers().contains("4"));
        assertEquals(2, response.getEvenNumbers().size());

        // Verify alphabets are converted to uppercase
        assertTrue(response.getAlphabets().contains("A"));
        assertTrue(response.getAlphabets().contains("R"));
        assertEquals(2, response.getAlphabets().size());

        // Verify special characters
        assertTrue(response.getSpecialCharacters().contains("$"));
        assertEquals(1, response.getSpecialCharacters().size());

        // Verify sum (1 + 334 + 4 = 339)
        assertEquals("339", response.getSum());

        // Verify concat string: A + R = AR, reversed = RA, alternating caps = Ra
        assertEquals("Ra", response.getConcatString());
    }

    /**
     * Test 2: Only alphabets
     * Input: ["A","ABCD","DOE"]
     */
    @Test
    @DisplayName("Test only alphabetic input")
    void testOnlyAlphabets() {
        // Arrange
        RequestDTO request = new RequestDTO(Arrays.asList("A", "ABCD", "DOE"));

        // Act
        ResponseDTO response = bfhlService.processRequest(request);

        // Assert
        assertTrue(response.getIsSuccess());
        assertTrue(response.getOddNumbers().isEmpty());
        assertTrue(response.getEvenNumbers().isEmpty());
        assertTrue(response.getSpecialCharacters().isEmpty());
        assertEquals("0", response.getSum());

        // Verify alphabets (A + ABCD + DOE = AABCDDOE)
        assertEquals(8, response.getAlphabets().size());
        assertTrue(response.getAlphabets().contains("A"));

        // Verify concat string
        // Concatenated: AABCDDOE
        // Reversed: EODDDCBAA
        // Alternating: EoDdCbAa
        assertEquals("EoDdCbAa", response.getConcatString());
    }

    /**
     * Test 3: Only numbers
     * Input: ["1", "2", "3", "4", "5"]
     */
    @Test
    @DisplayName("Test only numeric input")
    void testOnlyNumbers() {
        // Arrange
        RequestDTO request = new RequestDTO(Arrays.asList("1", "2", "3", "4", "5"));

        // Act
        ResponseDTO response = bfhlService.processRequest(request);

        // Assert
        assertTrue(response.getIsSuccess());
        assertTrue(response.getAlphabets().isEmpty());
        assertTrue(response.getSpecialCharacters().isEmpty());

        // Verify odd numbers
        assertEquals(3, response.getOddNumbers().size());
        assertTrue(response.getOddNumbers().containsAll(Arrays.asList("1", "3", "5")));

        // Verify even numbers
        assertEquals(2, response.getEvenNumbers().size());
        assertTrue(response.getEvenNumbers().containsAll(Arrays.asList("2", "4")));

        // Verify sum (1+2+3+4+5 = 15)
        assertEquals("15", response.getSum());

        // Concat string should be empty
        assertEquals("", response.getConcatString());
    }

    /**
     * Test 4: Empty array
     * Input: []
     */
    @Test
    @DisplayName("Test empty array throws exception")
    void testEmptyArray() {
        // Arrange
        RequestDTO request = new RequestDTO(Collections.emptyList());

        // Act & Assert
        BfhlException exception = assertThrows(BfhlException.class, () -> {
            bfhlService.processRequest(request);
        });

        assertEquals("Request data cannot be null or empty", exception.getMessage());
        assertEquals("INVALID_INPUT", exception.getErrorCode());
    }

    /**
     * Test 5: Special characters
     * Input: ["!", "@", "#", "$", "%"]
     */
    @Test
    @DisplayName("Test special characters handling")
    void testSpecialCharacters() {
        // Arrange
        RequestDTO request = new RequestDTO(Arrays.asList("!", "@", "#", "$", "%"));

        // Act
        ResponseDTO response = bfhlService.processRequest(request);

        // Assert
        assertTrue(response.getIsSuccess());
        assertTrue(response.getOddNumbers().isEmpty());
        assertTrue(response.getEvenNumbers().isEmpty());
        assertTrue(response.getAlphabets().isEmpty());
        assertEquals("0", response.getSum());

        // Verify special characters
        assertEquals(5, response.getSpecialCharacters().size());
        assertTrue(response.getSpecialCharacters().containsAll(Arrays.asList("!", "@", "#", "$", "%")));

        // Concat string should be empty
        assertEquals("", response.getConcatString());
    }

    /**
     * Test 6: Null request
     */
    @Test
    @DisplayName("Test null request throws exception")
    void testNullRequest() {
        // Act & Assert
        BfhlException exception = assertThrows(BfhlException.class, () -> {
            bfhlService.processRequest(null);
        });

        assertEquals("INVALID_INPUT", exception.getErrorCode());
    }

    /**
     * Test 7: Null data field
     */
    @Test
    @DisplayName("Test null data field throws exception")
    void testNullDataField() {
        // Arrange
        RequestDTO request = new RequestDTO(null);

        // Act & Assert
        BfhlException exception = assertThrows(BfhlException.class, () -> {
            bfhlService.processRequest(request);
        });

        assertEquals("INVALID_INPUT", exception.getErrorCode());
    }

    /**
     * Test 8: Mixed case alphabets should be converted to uppercase
     */
    @Test
    @DisplayName("Test lowercase alphabets are converted to uppercase")
    void testLowercaseAlphabets() {
        // Arrange
        RequestDTO request = new RequestDTO(Arrays.asList("abc", "def"));

        // Act
        ResponseDTO response = bfhlService.processRequest(request);

        // Assert
        assertTrue(response.getIsSuccess());
        assertEquals(6, response.getAlphabets().size());
        // Individual characters are extracted: A, B, C, D, E, F
        assertTrue(response.getAlphabets().containsAll(Arrays.asList("A", "B", "C", "D", "E", "F")));
    }

    /**
     * Test 9: Large numbers
     */
    @Test
    @DisplayName("Test large number handling")
    void testLargeNumbers() {
        // Arrange
        RequestDTO request = new RequestDTO(Arrays.asList("1000", "2000", "3000"));

        // Act
        ResponseDTO response = bfhlService.processRequest(request);

        // Assert
        assertTrue(response.getIsSuccess());
        assertTrue(response.getOddNumbers().isEmpty());
        assertEquals(3, response.getEvenNumbers().size());
        // Sum: 1000 + 2000 + 3000 = 6000
        assertEquals("6000", response.getSum());
    }

    /**
     * Test 10: Single element
     */
    @Test
    @DisplayName("Test single element input")
    void testSingleElement() {
        // Arrange
        RequestDTO request = new RequestDTO(Arrays.asList("A"));

        // Act
        ResponseDTO response = bfhlService.processRequest(request);

        // Assert
        assertTrue(response.getIsSuccess());
        assertEquals(1, response.getAlphabets().size());
        assertTrue(response.getAlphabets().contains("A"));
        assertEquals("A", response.getConcatString()); // Single char reversed is same, uppercase
    }

    /**
     * Test 11: Sum calculation accuracy
     */
    @Test
    @DisplayName("Test sum calculation accuracy")
    void testSumCalculation() {
        // Arrange
        RequestDTO request = new RequestDTO(Arrays.asList("10", "20", "30", "40"));

        // Act
        ResponseDTO response = bfhlService.processRequest(request);

        // Assert
        assertTrue(response.getIsSuccess());
        // Sum: 10 + 20 + 30 + 40 = 100
        assertEquals("100", response.getSum());
    }

    /**
     * Test 12: Response structure validation
     */
    @Test
    @DisplayName("Test response structure contains all required fields")
    void testResponseStructure() {
        // Arrange
        RequestDTO request = new RequestDTO(Arrays.asList("1", "a", "!"));

        // Act
        ResponseDTO response = bfhlService.processRequest(request);

        // Assert
        assertNotNull(response.getIsSuccess());
        assertNotNull(response.getUserId());
        assertNotNull(response.getEmail());
        assertNotNull(response.getRollNumber());
        assertNotNull(response.getOddNumbers());
        assertNotNull(response.getEvenNumbers());
        assertNotNull(response.getAlphabets());
        assertNotNull(response.getSpecialCharacters());
        assertNotNull(response.getSum());
        assertNotNull(response.getConcatString());
    }
}
