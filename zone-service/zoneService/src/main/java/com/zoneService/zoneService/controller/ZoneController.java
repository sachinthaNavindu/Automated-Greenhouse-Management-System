package com.zoneService.zoneService.controller;

import com.zoneService.zoneService.repository.ZoneRepo;
import com.zoneService.zoneService.entity.Zone;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/zones")
public class ZoneController {

    private final ZoneRepo repo;

    public ZoneController(ZoneRepo repo) {
        this.repo = repo;
    }

    @PostMapping
    public Zone createZone(@RequestBody Zone zone) {

        // BUSINESS RULE
        if(zone.getMinTemp() >= zone.getMaxTemp()){
            throw new RuntimeException("Invalid temperature range");
        }

        return repo.save(zone);
    }

    @GetMapping("/{id}")
    public Zone getZone(@PathVariable String id){
        return repo.findById(id).orElseThrow();
    }
}