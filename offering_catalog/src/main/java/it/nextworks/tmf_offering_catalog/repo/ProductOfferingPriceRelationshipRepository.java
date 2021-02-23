package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingPriceRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOfferingPriceRelationshipRepository
        extends JpaRepository<ProductOfferingPriceRelationship, String> { }
