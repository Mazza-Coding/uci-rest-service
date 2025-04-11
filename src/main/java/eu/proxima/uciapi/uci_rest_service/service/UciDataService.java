package eu.proxima.uciapi.uci_rest_service.service;

import org.springframework.stereotype.Service;

import eu.proxima.uciapi.uci_rest_service.dto.GetDataResponse;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UciDataService {

    /**
     * Generate mock data for the given date range
     * TODO: Fetch data from database
     */
    public GetDataResponse getDataForDateRange(LocalDate dateFrom, LocalDate dateTo) {
        log.debug("Generating data for period: {} to {}", dateFrom, dateTo);

        // Calculate number of days to determine amount of data to generate
        long daysBetween = ChronoUnit.DAYS.between(dateFrom, dateTo.plusDays(1));

        // Generate more data for larger date ranges (TESTING)
        int baseCount = (int) Math.min(daysBetween * 2, 20);

        GetDataResponse response = new GetDataResponse();
        response.setSinistri(generateRandomUuids(baseCount));
        response.setSinistriDettaglio(generateRandomUuids(baseCount / 2));
        response.setCause(null); // As per specification, this should be null
        response.setMessaggi(generateRandomUuids(baseCount * 2));
        response.setDocumenti(generateRandomUuids(baseCount));

        log.debug("Generated response with {} sinistri, {} messaggi",
                response.getSinistri().size(),
                response.getMessaggi().size());

        return response;
    }

    /**
     * Generate a list of random UUIDs
     */
    private List<String> generateRandomUuids(int count) {
        List<String> uuids = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            uuids.add(UUID.randomUUID().toString());
        }
        return uuids;
    }
}