package com.zoneService.zoneService.service;

import com.zoneService.zoneService.entity.Zone;

import java.util.List;

public interface ZoneService {
    Zone save(Zone zone);

    List<Zone> getAll();

    Zone getById(String id);

    Zone update(String id, Zone zone);

    void delete(String id);
}
