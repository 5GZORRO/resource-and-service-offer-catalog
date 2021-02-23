package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingRef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOfferingRefRepository extends JpaRepository<ProductOfferingRef, String> { }
