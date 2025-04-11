package eu.proxima.uciapi.uci_rest_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO for login request parameters to /Account/CreateToken endpoint
 */
@Data
@Schema(description = "Login request with authentication credentials")
public class LoginRequest {
    @Schema(description = "User's username", example = "user123", required = true)
    private String username;

    @Schema(description = "User's password", example = "password123", required = true)
    private String password;

    @Schema(description = "Company identifier", example = "COMPANY01", required = true)
    private String companyId;
}