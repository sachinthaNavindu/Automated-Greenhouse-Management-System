package com.zoneService.zoneService.controller;

import com.zoneService.zoneService.entity.Zone;
import com.zoneService.zoneService.service.ZoneService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zones")
public class ZoneController {

    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @PostMapping
    public Zone create(@RequestBody Zone zone) {
        return zoneService.save(zone);
    }

    @GetMapping
    public List<Zone> getAll() {
        return zoneService.getAll();
    }

    @GetMapping("/{id}")
    public Zone getById(@PathVariable String id) {
        return zoneService.getById(id);
    }

    @PutMapping("/{id}")
    public Zone update(@PathVariable String id, @RequestBody Zone zone) {
        return zoneService.update(id, zone);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        zoneService.delete(id);
    }
}