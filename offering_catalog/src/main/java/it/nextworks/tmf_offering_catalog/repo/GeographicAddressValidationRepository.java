package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddressValidation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeographicAddressValidationRepository extends JpaRepository<GeographicAddressValidation, String> {
}
