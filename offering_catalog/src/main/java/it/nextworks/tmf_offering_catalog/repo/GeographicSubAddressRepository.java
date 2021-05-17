package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.GeographicSubAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GeographicSubAddressRepository extends JpaRepository<GeographicSubAddress, String> {

    List<GeographicSubAddress> findAll();

    @Query("SELECT ga FROM GeographicSubAddress ga WHERE ga.id = ?1")
    Optional<GeographicSubAddress> findByGeographicSubAddressId(String id);

}
