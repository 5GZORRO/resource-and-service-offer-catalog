package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.product.ProductSpecificationRef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSpecificationRefRepository extends JpaRepository<ProductSpecificationRef, String> { }
