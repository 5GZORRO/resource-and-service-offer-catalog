package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingPriceRef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOfferingPriceRefRepository extends JpaRepository<ProductOfferingPriceRef, String> { }
