package lk.ijse.sensorservice.tokenManager;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class Token {

    @Value("${iot.api.base-url}")
    private String authBaseUrl;

    @Value("${iot.credentials.username}")
    private String userId;

    @Value("${iot.credentials.password}")
    private String secretKey;

    private String jwtToken;
    private String renewToken;

    private final WebClient httpClient;

    public Token(WebClient.Builder webClientBuilder) {
        this.httpClient = webClientBuilder.build();
    }

    @PostConstruct
    public void initializeSession() {
        authenticate();
    }

    public synchronized void authenticate() {
        try {
            Map<String, String> response = httpClient.post()
                    .uri(authBaseUrl + "/auth/login")
                    .bodyValue(Map.of("username", userId, "password", secretKey))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response != null) {
                this.jwtToken = response.get("accessToken");
                this.renewToken = response.get("refreshToken");
            }
        } catch (Exception ex) {
            System.err.println("IoT login failed: " + ex.getMessage());
        }
    }

    public synchronized String fetchAccessToken() {
        return jwtToken;
    }

    public synchronized void renewAccessToken() {
        try {
            Map<String, String> response = httpClient.post()
                    .uri(authBaseUrl + "/auth/refresh")
                    .bodyValue(Map.of("refreshToken", renewToken))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response != null) {
                this.jwtToken = response.get("accessToken");
            }
        } catch (Exception ex) {
            System.err.println("Token refresh failed: " + ex.getMessage());
            authenticate();
        }
    }
}