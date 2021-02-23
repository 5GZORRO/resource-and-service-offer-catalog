package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.ProductSpecificationRef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSpecificationRefRepository extends JpaRepository<ProductSpecificationRef, String> { }
