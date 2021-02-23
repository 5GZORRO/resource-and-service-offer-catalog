package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.ProductSpecificationCharacteristicValueUse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSpecificationCharacteristicValueUseRepository
        extends JpaRepository<ProductSpecificationCharacteristicValueUse, String> { }
