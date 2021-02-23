package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.ProductSpecificationCharacteristicValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSpecificationCharacteristicValueRepository
        extends JpaRepository<ProductSpecificationCharacteristicValue, String> { }
