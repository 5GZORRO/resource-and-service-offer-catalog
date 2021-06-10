package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.GeographicPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeographicPointRepository extends JpaRepository<GeographicPoint, String> {
}
