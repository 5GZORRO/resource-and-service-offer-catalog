package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.GeographicLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeographicLocationRepository extends JpaRepository<GeographicLocation, String> {
}
