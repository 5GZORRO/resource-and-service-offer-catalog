package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.ProductSpecificationCharacteristicRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSpecificationCharacteristicRelationshipRepository
        extends JpaRepository<ProductSpecificationCharacteristicRelationship, String> { }
