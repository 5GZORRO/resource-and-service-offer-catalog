package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.TimePeriod;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceSpecCharacteristic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResourceSpecCharacteristicRepository extends JpaRepository<ResourceSpecCharacteristic, String> {

    List<ResourceSpecCharacteristic> findByDescription(String description);

    List<ResourceSpecCharacteristic> findByName(String name);

    Optional<ResourceSpecCharacteristic> findById(String uuid);

    List<ResourceSpecCharacteristic> findByValidFor(TimePeriod validFor);

    List<ResourceSpecCharacteristic> findAll();
}
