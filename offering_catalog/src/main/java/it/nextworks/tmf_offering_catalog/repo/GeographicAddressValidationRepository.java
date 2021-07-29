package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddressValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GeographicAddressValidationRepository
        extends JpaRepository<GeographicAddressValidation, String> {

    @Query("SELECT gav FROM GeographicAddressValidation gav WHERE gav.id = ?1")
    Optional<GeographicAddressValidation> findByGeographicAddressValidationId(String id);

    List<GeographicAddressValidation> findAll();
}
