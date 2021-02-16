package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.product.ProductSpecificationRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSpecificationRelationshipRepository
        extends JpaRepository<ProductSpecificationRelationship, String> { }
