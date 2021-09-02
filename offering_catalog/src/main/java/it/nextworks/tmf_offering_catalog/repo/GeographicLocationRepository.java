package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.GeographicLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GeographicLocationRepository extends JpaRepository<GeographicLocation, String> {

    @Query("SELECT gl FROM GeographicLocation gl WHERE gl.id = ?1")
    Optional<GeographicLocation> findByGeographicLocationId(String id);
}
