package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.ProductOffering;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOfferingRepository extends JpaRepository<ProductOffering, String> { }
