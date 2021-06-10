package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.kafka.ExternalGeographicAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExternalGeographicAddressRepository extends JpaRepository<ExternalGeographicAddress, String> {
}
