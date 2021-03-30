package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.common.Any;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceSpecCharacteristicValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceSpecCharacteristicValueRepository extends JpaRepository<ServiceSpecCharacteristicValue, String> {

    List<ServiceSpecCharacteristicValue> findByValueType(String valueType);

    List<ServiceSpecCharacteristicValue> findByValue(Any value);

    Optional<ServiceSpecCharacteristicValue> findById(String uuid);

    List<ServiceSpecCharacteristicValue> findAll();
}
