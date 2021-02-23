package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.ProductSpecificationRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSpecificationRelationshipRepository
        extends JpaRepository<ProductSpecificationRelationship, String> { }
