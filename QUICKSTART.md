# Quick Start Commands

## Prerequisites
- Java 17+
- Maven 3.8+

## Build & Run

```bash
# Navigate to project
cd "c:\Users\HP\Downloads\New folder\bfhl"

# Build project (compiles, tests, packages)
mvn clean install

# Run application
mvn spring-boot:run
```

**API Endpoint:** http://localhost:8080/api/bfhl

## Test

```bash
# Run all tests
mvn test

# Run specific test
mvn test -Dtest=BFHLServiceImplTest#testMixedInput
```

## Example Request (cURL)

```bash
curl -X POST http://localhost:8080/api/bfhl \
  -H "Content-Type: application/json" \
  -d '{"data":["a","1","334","4","R","$"]}'
```

## Expected Response

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

## Import Postman Collection

1. Open Postman
2. File → Import
3. Select: `BFHL-API-Postman-Collection.json`
4. 10 test requests ready to use

---

For detailed documentation, see README.md and SETUP_GUIDE.md
