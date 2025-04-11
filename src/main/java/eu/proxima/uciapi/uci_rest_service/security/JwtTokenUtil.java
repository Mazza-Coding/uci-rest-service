package eu.proxima.uciapi.uci_rest_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import eu.proxima.uciapi.uci_rest_service.config.JwtConfigProperties;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    // Default token validity period in seconds (30 minutes)
    public static final long JWT_TOKEN_VALIDITY = 30 * 60;

    private final JwtConfigProperties jwtConfigProperties;

    /**
     * Retrieves username from jwt token
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Retrieves companyId from jwt token
     */
    public String getCompanyIdFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims.get("companyId", String.class);
    }

    /**
     * Retrieves expiration date from jwt token
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Extracts a specific claim from the token using the provided claims resolver
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Validates the token against provided user details
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Generates token for user with username and companyId claims
     */
    public String generateToken(String username, String companyId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("companyId", companyId);
        return doGenerateToken(claims, username);
    }

    /**
     * Calculate token expiration in seconds
     */
    public int getExpirationInSeconds() {
        return (int) JWT_TOKEN_VALIDITY;
    }

    // Private helper methods

    /**
     * Retrieving any information from token requires the secret key
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtConfigProperties.getSecret()).parseClaimsJws(token).getBody();
    }

    /**
     * Check if the token has expired
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Generate token with claims, subject, issued at, and expiration time
     * Sign it using HS256 algorithm and secret key
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS256, jwtConfigProperties.getSecret())
                .compact();
    }
}