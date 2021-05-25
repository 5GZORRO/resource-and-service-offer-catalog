package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeographicAddressRepository extends JpaRepository<GeographicAddress, String> {

}
