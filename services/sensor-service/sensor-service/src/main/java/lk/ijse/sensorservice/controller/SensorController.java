package lk.ijse.sensorservice.controller;

import lk.ijse.sensorservice.service.Scheduler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {
    private Scheduler Scheduler;

    @GetMapping("/latest")
    public Map<Long, Map<String, Object>> getLatest() {
        return Scheduler.getLatestReadings();
    }
}
