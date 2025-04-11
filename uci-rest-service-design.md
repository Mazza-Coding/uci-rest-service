Design Document: UCI REST Service

Project Title: UCI REST Service – Token Authentication and Data Retrieval API
Technologies: Java 1.8, Spring Boot 2.7.18, Maven, jjwt (0.9.1)
Author: Simone Mazza
Date: 11/04/2025

---

1. Objective

Develop a REST API service that:

- Provides JWT-based authentication (`/Account/CreateToken`)
- Serves data between two dates (`/api/uciSearch/GetData`)

---

2. Endpoints Overview

2.1. /Account/CreateToken

- Method: POST
- Input Parameters:
  - username (String)
  - password (String)
  - companyId (String)
- Output (JSON):
  {
  "token": "valore_token",
  "expiresIn": 0,
  "tokenType": "Bearer",
  "error": "eventuale messaggio di errore"
  }
- Purpose: Returns a signed JWT token if authentication is successful.

  2.2. /api/uciSearch/GetData

- Method: GET
- Query Parameters:
  - dateFrom (YYYY-MM-DD)
  - dateTo (YYYY-MM-DD)
- Output (JSON):
  {
  "sinistri": ["UUID"],
  "sinistriDettaglio": ["UUID"],
  "cause": null,
  "messaggi": ["UUID", ...],
  "documenti": ["UUID", ...]
  }
- Purpose: Retrieves random UUIDs representing sinistri, dettagli, messaggi, and documenti.

---

3. Step-by-Step Development Plan

Step 1: Project Setup

- Initialize a Spring Boot 2.7.18 project using Maven
- Set Java version to 1.8 in pom.xml
- Add dependencies:
  - spring-boot-starter-web
  - spring-boot-starter-security
  - jjwt:0.9.1
  - lombok (optional)

Step 2: Define DTOs

- LoginRequest: for /CreateToken input
- TokenResponse: for /CreateToken output
- GetDataResponse: for /GetData output

Step 3: Implement JWT Utility Class

- Use io.jsonwebtoken.Jwts to generate and parse JWTs
- Token includes claims like username and companyId
- Configure expiration and signing key (HMAC256)

Step 4: Implement Authentication Controller

- Validate credentials (stub or real service)
- If valid, return TokenResponse with JWT
- If invalid, return error message

Step 5: Implement Data Retrieval Controller

- Create /api/uciSearch/GetData
- Validate JWT (using Spring Security filter or interceptor)
- Generate mock UUIDs for each field (sinistri, messaggi, etc.)
- Return a structured JSON response

Step 6: Configure Spring Security

- Disable CSRF
- Secure /api/\*\* with JWT filter
- Leave /Account/CreateToken open

Step 7: Testing

- Unit test JWT creation and parsing
- Test /CreateToken with valid/invalid credentials
- Test /GetData with and without token

Step 8: Documentation and Final Review

- Add Swagger or simple API docs
- Review and test edge cases

---

4. Security Considerations

- Use a strong, securely stored secret key
- Set token expiration (e.g., 15–30 minutes)
- Optionally, support refresh tokens in future
- Sanitize and validate all inputs

---

5. Future Enhancements

- Database integration for authentication and data
- Logging and audit trails
- Pagination and filtering on GetData
- Refresh token support

---

6. Conclusion
   This design outlines a minimal but secure and extensible JWT-based authentication and data API built with Java 8 and Spring Boot 2.7.18. It is suitable for internal systems requiring lightweight token-based auth and mock data response for given date ranges.
