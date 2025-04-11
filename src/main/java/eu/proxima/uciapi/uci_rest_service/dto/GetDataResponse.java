package eu.proxima.uciapi.uci_rest_service.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO for data response from /api/uciSearch/GetData endpoint
 */
@Data
@Schema(description = "Data response containing lists of UUIDs for various entities")
public class GetDataResponse {
    @Schema(description = "List of sinistri UUIDs", example = "[\"123e4567-e89b-12d3-a456-426614174000\"]", required = true)
    private List<String> sinistri;

    @Schema(description = "List of sinistri details UUIDs", example = "[\"123e4567-e89b-12d3-a456-426614174001\"]", required = true)
    private List<String> sinistriDettaglio;

    @Schema(description = "List of cause UUIDs, always null as per specification", example = "null", required = false)
    private List<String> cause;

    @Schema(description = "List of messaggi UUIDs", example = "[\"123e4567-e89b-12d3-a456-426614174002\"]", required = true)
    private List<String> messaggi;

    @Schema(description = "List of documenti UUIDs", example = "[\"123e4567-e89b-12d3-a456-426614174003\"]", required = true)
    private List<String> documenti;
}