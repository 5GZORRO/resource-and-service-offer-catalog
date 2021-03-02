package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductOfferingPriceRepository extends JpaRepository<ProductOfferingPrice, String> {

    @Query("SELECT pop FROM ProductOfferingPrice pop WHERE pop.id = ?1")
    Optional<ProductOfferingPrice> findByProductOfferingPriceId(String id);

    List<ProductOfferingPrice> findAll();
}
