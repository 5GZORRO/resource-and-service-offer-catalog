package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.Any;
import it.nextworks.tmf_offering_catalogue.information_models.resource.ResourceSpecCharacteristicValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResourceSpecCharacteristicValueRepository extends JpaRepository<ResourceSpecCharacteristicValue, String> {

    List<ResourceSpecCharacteristicValue> findByValueType(String valueType);

    List<ResourceSpecCharacteristicValue> findByValue(Any value);

    Optional<ResourceSpecCharacteristicValue> findById(String uuid);

    List<ResourceSpecCharacteristicValue> findAll();
}
