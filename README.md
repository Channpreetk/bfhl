# BFHL API - Spring Boot 3 REST API

A complete Spring Boot 3 REST API project built with Java 17 and Maven, implementing the BFHL (Binary, Functional, Hexadecimal, Letter) processing logic.

## Project Overview

This project processes an array of mixed data types (numbers, alphabets, and special characters) and categorizes them according to specific business logic.

## Project Structure

```
bfhl/
├── pom.xml
├── README.md
├── .gitignore
│
├── src/
│   ├── main/
│   │   ├── java/com/bfhl/
│   │   │   ├── BfhlApplication.java
│   │   │   │
│   │   │   ├── controller/
│   │   │   │   └── BFHLController.java
│   │   │   │
│   │   │   ├── service/
│   │   │   │   ├── BFHLService.java
│   │   │   │   └── impl/
│   │   │   │       └── BFHLServiceImpl.java
│   │   │   │
│   │   │   ├── dto/
│   │   │   │   ├── RequestDTO.java
│   │   │   │   └── ResponseDTO.java
│   │   │   │
│   │   │   └── exception/
│   │   │       ├── BfhlException.java
│   │   │       ├── ErrorResponse.java
│   │   │       └── GlobalExceptionHandler.java
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/com/bfhl/service/
│           └── BFHLServiceImplTest.java
```

## Package Description

### controller
- **BFHLController**: REST controller with POST endpoint at `/api/bfhl`

### service
- **BFHLService**: Service interface defining the contract for BFHL operations
- **BFHLServiceImpl**: Implementation of the service with complete business logic

### dto
- **RequestDTO**: Data Transfer Object for incoming requests containing data array
- **ResponseDTO**: Data Transfer Object for API responses with categorized data

### exception
- **BfhlException**: Custom exception for BFHL-specific errors
- **ErrorResponse**: Structured error response format
- **GlobalExceptionHandler**: Centralized exception handling for the API

## Technology Stack

- **Java**: 17
- **Spring Boot**: 3.3.0
- **Maven**: 3.8+
- **JUnit**: 5
- **Lombok**: For reducing boilerplate code
- **Spring Validation**: For request validation

## Prerequisites

1. Java 17 installed and configured
2. Maven 3.8 or higher installed
3. Git (optional)

## Installation & Setup

### 1. Clone or Download the Project

```bash
cd c:\Users\HP\Downloads\New folder\bfhl
```

### 2. Build the Project

```bash
mvn clean install
```

This command will:
- Clean previous build artifacts
- Download dependencies
- Compile the code
- Run all tests
- Package the application

## Running the Application

### Start the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

API endpoint: `http://localhost:8080/api/bfhl`

### Running Tests

```bash
mvn test
```

This will run all 12 JUnit 5 tests covering:
- Mixed input (numbers, alphabets, special characters)
- Only alphabets
- Only numbers
- Empty array scenarios
- Special characters
- Null handling
- Edge cases

### Run Specific Test

```bash
mvn test -Dtest=BFHLServiceImplTest
```

## API Documentation

### Endpoint: POST /api/bfhl

#### Request Format

```json
{
  "data": ["a","1","334","4","R","$"]
}
```

#### Response Format (HTTP 200 - Success)

```json
{
  "is_success": true,
  "user_id": "channpreet_kaur_17062004",
  "email": "channpreet.kaur@example.com",
  "roll_number": "12345678",
  "odd_numbers": ["1"],
  "even_numbers": ["334", "4"],
  "alphabets": ["A", "R"],
  "special_characters": ["$"],
  "sum": "339",
  "concat_string": "Ra"
}
```

#### Response Format (HTTP 400 - Error)

```json
{
  "is_success": false,
  "message": "Request data cannot be null or empty",
  "error_code": "INVALID_INPUT",
  "timestamp": "2024-06-24T10:30:00"
}
```

## API Processing Logic

### 1. Categorization

**Odd Numbers**: Numeric values where value % 2 != 0
- Returned as strings in `odd_numbers` array

**Even Numbers**: Numeric values where value % 2 == 0
- Returned as strings in `even_numbers` array

**Alphabets**: Alphabetic characters (a-zA-Z)
- Converted to UPPERCASE
- Returned in `alphabets` array

**Special Characters**: Any other characters
- Returned as-is in `special_characters` array

### 2. Sum Calculation

- Sum of all numeric values in the input
- Returned as a string in the `sum` field

### 3. Concat String Generation

The `concat_string` is generated with the following steps:

1. **Extract**: Collect all alphabetical characters
2. **Concatenate**: Combine them in order
3. **Reverse**: Reverse the entire concatenated string
4. **Alternate Capitalization**: Apply pattern where:
   - Index 0: UPPERCASE
   - Index 1: lowercase
   - Index 2: UPPERCASE
   - Index 3: lowercase
   - ... and so on

**Example**:
- Input alphabets: A, ABCD, DOE
- Extracted: A, A, B, C, D, D, O, E
- Concatenated: AABCDDOE
- Reversed: EODDDCBAA
- Alternating Caps: E(0-UP), o(1-low), D(2-UP), d(3-low), D(4-UP), c(5-low), B(6-UP), a(7-low), A(8-UP)
- Result: "EoDdDcBaA"

## Configuration

### application.properties

The application uses the following configuration:

```properties
# Server
server.port=8080
server.servlet.context-path=/api

# Logging
logging.level.root=INFO
logging.level.com.bfhl=DEBUG
```

### Service Constants

Located in `BFHLServiceImpl.java`:

```java
private static final String USER_ID = "channpreet_kaur_17062004";
private static final String EMAIL = "channpreet.kaur@example.com";
private static final String ROLL_NUMBER = "12345678";
```

To modify these values, edit the constants in `BFHLServiceImpl.java`.

## Postman Testing

### 1. Create a New Request

- Method: **POST**
- URL: `http://localhost:8080/api/bfhl`
- Headers: `Content-Type: application/json`

### 2. Example Request 1 - Mixed Input

**Request Body**:
```json
{
  "data": ["a","1","334","4","R","$"]
}
```

**Expected Response**:
```json
{
  "is_success": true,
  "user_id": "channpreet_kaur_17062004",
  "email": "channpreet.kaur@example.com",
  "roll_number": "12345678",
  "odd_numbers": ["1"],
  "even_numbers": ["334", "4"],
  "alphabets": ["A", "R"],
  "special_characters": ["$"],
  "sum": "339",
  "concat_string": "Ra"
}
```

### 3. Example Request 2 - Only Alphabets

**Request Body**:
```json
{
  "data": ["A","ABCD","DOE"]
}
```

**Expected Response**:
```json
{
  "is_success": true,
  "user_id": "channpreet_kaur_17062004",
  "email": "channpreet.kaur@example.com",
  "roll_number": "12345678",
  "odd_numbers": [],
  "even_numbers": [],
  "alphabets": ["A", "A", "B", "C", "D", "D", "O", "E"],
  "special_characters": [],
  "sum": "0",
  "concat_string": "EoDdDcBaA"
}
```

### 4. Example Request 3 - Only Numbers

**Request Body**:
```json
{
  "data": ["1", "2", "3", "4", "5"]
}
```

**Expected Response**:
```json
{
  "is_success": true,
  "user_id": "channpreet_kaur_17062004",
  "email": "channpreet.kaur@example.com",
  "roll_number": "12345678",
  "odd_numbers": ["1", "3", "5"],
  "even_numbers": ["2", "4"],
  "alphabets": [],
  "special_characters": [],
  "sum": "15",
  "concat_string": ""
}
```

### 5. Example Request 4 - Error Case

**Request Body**:
```json
{
  "data": []
}
```

**Expected Response** (HTTP 400):
```json
{
  "is_success": false,
  "message": "Request data cannot be null or empty",
  "error_code": "INVALID_INPUT",
  "timestamp": "2024-06-24T10:30:00"
}
```

## Build and Run Commands Summary

```bash
# Navigate to project directory
cd c:\Users\HP\Downloads\New folder\bfhl

# Clean build
mvn clean

# Build project (compile + test + package)
mvn clean install

# Run the application
mvn spring-boot:run

# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=BFHLServiceImplTest

# Run specific test method
mvn test -Dtest=BFHLServiceImplTest#testMixedInput

# Skip tests during build
mvn clean install -DskipTests

# Build fat JAR
mvn clean package

# Run generated JAR
java -jar target/bfhl-1.0.0.jar
```

## Testing Coverage

The project includes 12 comprehensive JUnit 5 tests:

1. **testMixedInput**: Tests mixed data (numbers, alphabets, special characters)
2. **testOnlyAlphabets**: Tests alphabetic input only
3. **testOnlyNumbers**: Tests numeric input only
4. **testEmptyArray**: Tests empty array error handling
5. **testSpecialCharacters**: Tests special character handling
6. **testNullRequest**: Tests null request handling
7. **testNullDataField**: Tests null data field handling
8. **testLowercaseAlphabets**: Tests lowercase to uppercase conversion
9. **testLargeNumbers**: Tests large number handling
10. **testSingleElement**: Tests single element input
11. **testSumCalculation**: Tests sum calculation accuracy
12. **testResponseStructure**: Tests response structure validation

## Code Features

- ✅ Spring Boot 3 with Java 17
- ✅ Constructor Injection for dependencies
- ✅ Comprehensive exception handling
- ✅ Request validation with @Valid
- ✅ Global exception handler
- ✅ Clean code and best practices
- ✅ Extensive unit tests (JUnit 5)
- ✅ Service layer abstraction
- ✅ DTOs for request/response
- ✅ Proper package structure
- ✅ Logging with SLF4J
- ✅ Lombok for reducing boilerplate

## Troubleshooting

### Port Already in Use

If port 8080 is already in use, change it in `application.properties`:

```properties
server.port=9090
```

### Java Version Mismatch

Ensure Java 17 is installed:
```bash
java -version
```

### Maven Not Found

Ensure Maven is in your PATH:
```bash
mvn -version
```

### Tests Failing

Run with more verbose output:
```bash
mvn test -e
```

## Project Metadata

- **Project Name**: bfhl
- **Version**: 1.0.0
- **Java Version**: 17
- **Spring Boot Version**: 3.3.0
- **Build Tool**: Maven 3.8+
- **License**: Open Source

## Author Notes

This project demonstrates:
- Spring Boot 3 best practices
- RESTful API design
- Proper service layer architecture
- Comprehensive unit testing
- Exception handling
- Request validation
- Clean code principles

## Support

For issues or questions, refer to:
- Spring Boot Documentation: https://spring.io/projects/spring-boot
- Maven Guide: https://maven.apache.org/guides/
- JUnit 5 Documentation: https://junit.org/junit5/

---

**Project Created**: June 24, 2024
