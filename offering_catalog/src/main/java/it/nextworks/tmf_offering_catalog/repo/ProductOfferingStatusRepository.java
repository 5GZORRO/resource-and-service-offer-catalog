package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingStatesEnum;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductOfferingStatusRepository extends JpaRepository<ProductOfferingStatus, String> {

    @Modifying
    @Query("UPDATE ProductOfferingStatus pos SET pos.status = ?2 WHERE pos.catalogId = ?1")
    int setStatusById(String catalogId, ProductOfferingStatesEnum productOfferingStatesEnum);

    Optional<ProductOfferingStatus> findById(String id);

    List<ProductOfferingStatus> findAll();

}
