# UCI REST Service

REST API service that provides JWT-based authentication and data retrieval for UCI use cases.

## Features

- JWT-based authentication with `/Account/CreateToken` endpoint
- Data retrieval with date filtering via `/api/uciSearch/GetData` endpoint
- OpenAPI/Swagger documentation for easy API exploration
- Secure endpoints with proper error handling

## API Endpoints

### Authentication

#### POST /Account/CreateToken

Creates a JWT token for the provided credentials.

**Request:**

```json
{
  "username": "user123",
  "password": "password123",
  "companyId": "COMPANY01"
}
```

**Response:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "expiresIn": 1800,
  "tokenType": "Bearer",
  "error": null
}
```

### Data Retrieval

#### GET /api/uciSearch/GetData?dateFrom=2023-01-01&dateTo=2023-01-31

Retrieves data for the given date range. Requires authentication with JWT token.

**Headers:**

```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

**Response:**

```json
{
  "sinistri": ["123e4567-e89b-12d3-a456-426614174000"],
  "sinistriDettaglio": ["123e4567-e89b-12d3-a456-426614174001"],
  "cause": null,
  "messaggi": ["123e4567-e89b-12d3-a456-426614174002"],
  "documenti": ["123e4567-e89b-12d3-a456-426614174003"]
}
```

## API Documentation

The API is documented using OpenAPI/Swagger and can be accessed at:

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/api-docs

## Testing the API

### Using cURL

1. Get a token:

```bash
curl -X POST http://localhost:8080/Account/CreateToken \
  -H "Content-Type: application/json" \
  -d '{"username":"user123","password":"password123","companyId":"COMPANY01"}'
```

2. Use the token to get data:

```bash
curl -X GET "http://localhost:8080/api/uciSearch/GetData?dateFrom=2023-01-01&dateTo=2023-01-31" \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

### Using Swagger UI

1. Open http://localhost:8080/swagger-ui.html in your browser
2. Use the `/Account/CreateToken` endpoint to get a token
3. Authorize using the "Authorize" button and paste in your token
4. Test the `/api/uciSearch/GetData` endpoint with the desired date parameters

## Technologies

- Java 1.8
- Spring Boot 2.7.18
- JWT (jjwt 0.9.1)
- SpringDoc OpenAPI UI

## Running the Application

```bash
# Build the project
./mvnw clean package

# Run the application
java -jar target/uci-rest-service-0.0.1-SNAPSHOT.jar
```

Or using Spring Boot Maven plugin:

```bash
./mvnw spring-boot:run
```
