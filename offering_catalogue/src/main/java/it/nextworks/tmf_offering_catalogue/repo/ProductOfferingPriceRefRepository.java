package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.product.ProductOfferingPriceRef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductOfferingPriceRefRepository extends JpaRepository<ProductOfferingPriceRef, String> {

    Optional<ProductOfferingPriceRef> findByHref(String href);

    Optional<ProductOfferingPriceRef> findById(String id);

    List<ProductOfferingPriceRef> findByName(String name);

    List<ProductOfferingPriceRef> findAll();
}
