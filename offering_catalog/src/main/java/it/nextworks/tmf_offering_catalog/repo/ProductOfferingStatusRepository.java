package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductOfferingStatusRepository extends JpaRepository<ProductOfferingStatus, String> {

    Optional<ProductOfferingStatus> findById(String id);

    List<ProductOfferingStatus> findAll();

}
