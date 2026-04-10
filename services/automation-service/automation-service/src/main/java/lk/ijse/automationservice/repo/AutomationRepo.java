package lk.ijse.automationservice.repo;

import lk.ijse.automationservice.model.Automation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutomationRepo extends JpaRepository<Automation, Long> {
    List<Automation> findAllByOrderByTriggeredAtDesc();

}
