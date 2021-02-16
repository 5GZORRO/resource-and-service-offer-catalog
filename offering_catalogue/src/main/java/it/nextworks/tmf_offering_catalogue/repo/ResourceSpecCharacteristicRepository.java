package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.TimePeriod;
import it.nextworks.tmf_offering_catalogue.information_models.resource.ResourceSpecCharacteristic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResourceSpecCharacteristicRepository extends JpaRepository<ResourceSpecCharacteristic, String> {

    Optional<ResourceSpecCharacteristic> findByDescription(String description);

    Optional<ResourceSpecCharacteristic> findById(String id);

    List<ResourceSpecCharacteristic> findByName(String name);

    List<ResourceSpecCharacteristic> findByValidFor(TimePeriod validFor);

    List<ResourceSpecCharacteristic> findAll();


}
