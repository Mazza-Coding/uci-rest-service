package eu.proxima.uciapi.uci_rest_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.proxima.uciapi.uci_rest_service.dto.LoginRequest;
import eu.proxima.uciapi.uci_rest_service.dto.TokenResponse;
import eu.proxima.uciapi.uci_rest_service.security.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/Account")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentication", description = "Authentication API for obtaining JWT tokens")
public class AuthController {

    private final JwtTokenUtil jwtTokenUtil;

    @Operation(summary = "Create JWT token", description = "Validates user credentials and returns a JWT token if valid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful authentication", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content)
    })
    @PostMapping("/CreateToken")
    public ResponseEntity<TokenResponse> createToken(@RequestBody LoginRequest loginRequest) {
        log.info("Token request received for user: {}", loginRequest.getUsername());

        TokenResponse response = new TokenResponse();

        // TODO: Validate credentials against a database
        if (isValidCredentials(loginRequest.getUsername(), loginRequest.getPassword(), loginRequest.getCompanyId())) {
            String token = jwtTokenUtil.generateToken(loginRequest.getUsername(), loginRequest.getCompanyId());
            response.setToken(token);
            response.setExpiresIn(jwtTokenUtil.getExpirationInSeconds());
            response.setTokenType("Bearer");
            log.info("Token generated successfully for user: {}", loginRequest.getUsername());
        } else {
            response.setError("Invalid credentials");
            log.warn("Invalid credentials for user: {}", loginRequest.getUsername());
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Simulates credential validation
     */
    private boolean isValidCredentials(String username, String password, String companyId) {
        // For demonstration, accept any non-null values
        return username != null && !username.trim().isEmpty()
                && password != null && !password.trim().isEmpty()
                && companyId != null && !companyId.trim().isEmpty();
    }
}