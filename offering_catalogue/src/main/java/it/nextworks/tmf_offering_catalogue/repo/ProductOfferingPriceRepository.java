package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.product.ProductOfferingPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOfferingPriceRepository extends JpaRepository<ProductOfferingPrice, String> { }
