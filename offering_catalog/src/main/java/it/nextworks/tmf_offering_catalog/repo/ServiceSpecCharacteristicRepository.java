package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.TimePeriod;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceSpecCharacteristic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceSpecCharacteristicRepository extends JpaRepository<ServiceSpecCharacteristic, String> {

    List<ServiceSpecCharacteristic> findByDescription(String description);

    List<ServiceSpecCharacteristic> findByName(String name);

    Optional<ServiceSpecCharacteristic> findById(String uuid);

    List<ServiceSpecCharacteristic> findByValidFor(TimePeriod validFor);

    List<ServiceSpecCharacteristic> findAll();
}
