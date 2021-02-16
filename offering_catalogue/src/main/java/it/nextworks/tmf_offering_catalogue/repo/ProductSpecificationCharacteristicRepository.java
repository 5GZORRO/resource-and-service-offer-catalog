package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.product.ProductSpecificationCharacteristic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSpecificationCharacteristicRepository
        extends JpaRepository<ProductSpecificationCharacteristic, String> { }
