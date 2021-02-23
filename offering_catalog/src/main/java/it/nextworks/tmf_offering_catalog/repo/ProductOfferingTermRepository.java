package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingTerm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOfferingTermRepository extends JpaRepository<ProductOfferingTerm, String> { }
