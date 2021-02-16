package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.resource.ResourceSpecCharacteristicValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceSpecCharacteristicValueRepository extends JpaRepository<ResourceSpecCharacteristicValue, String> { }
