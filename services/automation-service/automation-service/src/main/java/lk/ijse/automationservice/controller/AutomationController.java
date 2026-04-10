package lk.ijse.automationservice.controller;


import lk.ijse.automationservice.model.Automation;
import lk.ijse.automationservice.service.AutomationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/automation")
public class AutomationController {


    private AutomationService automationService;

    @PostMapping("/process")
    public void process(@RequestBody Map<String, Object> telemetry) {
        automationService.processTelemetry(telemetry);
    }

    @GetMapping("/logs")
    public List<Automation> getLogs() {
        return automationService.getAllLogs();
    }
}
