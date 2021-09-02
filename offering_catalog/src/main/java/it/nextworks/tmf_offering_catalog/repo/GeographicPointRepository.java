package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.GeographicPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GeographicPointRepository extends JpaRepository<GeographicPoint, String> {

    @Query("SELECT gp FROM GeographicPoint gp WHERE gp.id = ?1")
    Optional<GeographicPoint> findByGeographicPointId(String id);
}
