package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.product.ProductSpecificationCharacteristicValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSpecificationCharacteristicValueRepository
        extends JpaRepository<ProductSpecificationCharacteristicValue, String> { }
