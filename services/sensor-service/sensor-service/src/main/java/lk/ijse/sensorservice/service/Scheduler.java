package lk.ijse.sensorservice.service;



import lk.ijse.sensorservice.client.AutomationClient;
import lk.ijse.sensorservice.client.ZoneClient;
import lk.ijse.sensorservice.tokenManager.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class Scheduler {

    private Token token;

    private ZoneClient zoneClient;

    private AutomationClient automationClient;

    private final WebClient httpClient;
    private final Map<Long, Map<String, Object>> cacheStore = new ConcurrentHashMap<>();

    @Value("${iot.api.base-url}")
    private String deviceBaseUrl;

    public Scheduler(WebClient.Builder builder) {
        this.httpClient = builder.build();
    }

    @Scheduled(fixedDelay = 10000)
    public void syncTelemetry() {
        System.out.println("Collecting sensor telemetry...");

        try {
            List<Map<String, Object>> zoneList = zoneClient.getAllZones();

            for (Map<String, Object> zoneData : zoneList) {

                String sensorId = (String) zoneData.get("deviceId");
                Long areaId = ((Number) zoneData.get("id")).longValue();

                if (sensorId != null) {
                    fetchTelemetryWithRetry(sensorId, areaId, true);
                }
            }

        } catch (Exception ex) {
            System.err.println("Zone fetch error: " + ex.getMessage());
        }
    }

    private void fetchTelemetryWithRetry(String sensorId, Long areaId, boolean retryAllowed) {

        try {
            Map payloadData = httpClient.get()
                    .uri(deviceBaseUrl + "/devices/telemetry/" + sensorId)
                    .header("Authorization", "Bearer " + token.fetchAccessToken())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (payloadData != null) {

                payloadData.put("zoneId", areaId);
                payloadData.put("deviceId", sensorId);

                cacheStore.put(areaId, payloadData);

                automationClient.process(payloadData);

                System.out.println("Telemetry pushed for sensor: " + sensorId);
            }

        } catch (WebClientResponseException.Unauthorized ex) {

            if (retryAllowed) {
                System.out.println("Access expired. Renewing token...");
                token.renewAccessToken();
                fetchTelemetryWithRetry(sensorId, areaId, false);
            } else {
                token.authenticate();
            }

        } catch (Exception ex) {
            System.err.println("Telemetry error for sensor " + sensorId + ": " + ex.getMessage());
        }
    }

    public Map<Long, Map<String, Object>> getLatestReadings() {
        return cacheStore;
    }
}