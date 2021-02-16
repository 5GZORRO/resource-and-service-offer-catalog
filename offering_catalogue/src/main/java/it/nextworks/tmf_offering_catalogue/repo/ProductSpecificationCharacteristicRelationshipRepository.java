package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.product.ProductSpecificationCharacteristicRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSpecificationCharacteristicRelationshipRepository
        extends JpaRepository<ProductSpecificationCharacteristicRelationship, String> { }
