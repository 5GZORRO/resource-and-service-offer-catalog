package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.Any;
import it.nextworks.tmf_offering_catalogue.information_models.resource.ResourceSpecCharacteristicValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceSpecCharacteristicValueRepository extends JpaRepository<ResourceSpecCharacteristicValue, String> {

    List<ResourceSpecCharacteristicValue> findByValue(Any value);

    List<ResourceSpecCharacteristicValue> findByValueType(String valueType);

    List<ResourceSpecCharacteristicValue> findAll();
}
