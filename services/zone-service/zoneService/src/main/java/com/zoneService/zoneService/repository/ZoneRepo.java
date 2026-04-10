    package com.zoneService.zoneService.repository;

    import com.zoneService.zoneService.entity.Zone;
    import org.springframework.data.jpa.repository.JpaRepository;

    public interface ZoneRepo extends JpaRepository<Zone, String> {
    }
