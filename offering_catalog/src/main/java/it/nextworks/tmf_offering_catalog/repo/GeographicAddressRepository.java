package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GeographicAddressRepository extends JpaRepository<GeographicAddress, String> {

    List<GeographicAddress> findAll();

    @Query("SELECT ga FROM GeographicAddress ga WHERE ga.id = ?1")
    Optional<GeographicAddress> findByGeographicAddressId(String id);

}
