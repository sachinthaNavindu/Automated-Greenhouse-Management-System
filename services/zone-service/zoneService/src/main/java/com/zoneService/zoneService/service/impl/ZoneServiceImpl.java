package com.zoneService.zoneService.service.impl;

import com.zoneService.zoneService.entity.Zone;
import com.zoneService.zoneService.repository.ZoneRepo;
import com.zoneService.zoneService.service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepo zoneRepo;
    private final RestTemplate restTemplate;

    private final String IOT_SERVICE_URL = "";

    @Override
    public Zone save(Zone zone) {

        if (zone.getMinTemp() >= zone.getMaxTemp()) {
            throw new RuntimeException("Invalid temperature range");
        }

        String deviceId;

        try {
            Map<String, Object> request = Map.of(
                    "name", "ZoneDevice-" + zone.getName(),
                    "zoneName", zone.getName()
            );

            Map response = restTemplate.postForObject(IOT_SERVICE_URL, request, Map.class);

            deviceId = response.get("deviceId").toString();

        } catch (Exception e) {
            deviceId = UUID.randomUUID().toString();
        }

        zone.setDeviceId(deviceId);

        return zoneRepo.save(zone);
    }

    @Override
    public List<Zone> getAll() {
        return zoneRepo.findAll();
    }

    @Override
    public Zone getById(String id) {
        return zoneRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone not found"));
    }

    @Override
    public Zone update(String id, Zone zone) {

        if (zone.getMinTemp() >= zone.getMaxTemp()) {
            throw new RuntimeException("Invalid temperature range");
        }

        Zone data = getById(id);

        data.setName(zone.getName());
        data.setMinTemp(zone.getMinTemp());
        data.setMaxTemp(zone.getMaxTemp());

        return zoneRepo.save(data);
    }

    @Override
    public void delete(String id) {
        zoneRepo.deleteById(id);
    }
}