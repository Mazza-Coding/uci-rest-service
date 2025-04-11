package eu.proxima.uciapi.uci_rest_service.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.proxima.uciapi.uci_rest_service.dto.GetDataResponse;
import eu.proxima.uciapi.uci_rest_service.service.UciDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/uciSearch")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "UCI Data", description = "API for retrieving UCI data based on date filters")
@SecurityRequirement(name = "Bearer Authentication")
public class UciSearchController {

    private final UciDataService uciDataService;

    @Operation(summary = "Retrieve UCI data", description = "Retrieves data (sinistri, messaggi, documenti, etc.) for the specified date range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful data retrieval", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetDataResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid date parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token is missing or invalid", content = @Content)
    })
    @GetMapping("/GetData")
    public ResponseEntity<GetDataResponse> getData(
            @Parameter(description = "Start date in YYYY-MM-DD format", required = true) @RequestParam("dateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFrom,

            @Parameter(description = "End date in YYYY-MM-DD format", required = true) @RequestParam("dateTo") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateTo) {

        log.info("Data request received for period: {} to {}", dateFrom, dateTo);

        // Validate date range
        if (dateFrom.isAfter(dateTo)) {
            log.warn("Invalid date range: {} to {}", dateFrom, dateTo);
            throw new IllegalArgumentException("dateFrom must be before or equal to dateTo");
        }

        // Get data from service
        GetDataResponse response = uciDataService.getDataForDateRange(dateFrom, dateTo);

        log.info("Data retrieved successfully for period: {} to {}", dateFrom, dateTo);
        return ResponseEntity.ok(response);
    }
}