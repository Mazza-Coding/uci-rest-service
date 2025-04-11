package eu.proxima.uciapi.uci_rest_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO for token response from /Account/CreateToken endpoint
 */
@Data
@Schema(description = "Authentication token response")
public class TokenResponse {
    @Schema(description = "JWT token value", example = "eyJhbGciOiJIUzI1NiJ9...", required = true)
    private String token;

    @Schema(description = "Token expiration time in seconds", example = "1800", required = true)
    private int expiresIn;

    @Schema(description = "Token type", example = "Bearer", required = true)
    private String tokenType = "Bearer";

    @Schema(description = "Error message if authentication fails", example = "Invalid credentials", required = false)
    private String error;
}