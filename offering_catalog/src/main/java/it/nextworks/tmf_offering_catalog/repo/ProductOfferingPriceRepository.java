package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOfferingPriceRepository extends JpaRepository<ProductOfferingPrice, String> { }
