package com.zoneService.zoneService.service.impl;

import com.zoneService.zoneService.entity.Zone;
import com.zoneService.zoneService.repository.ZoneRepo;
import com.zoneService.zoneService.service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepo zoneRepo;

    @Override
    public Zone save(Zone zone) {
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
