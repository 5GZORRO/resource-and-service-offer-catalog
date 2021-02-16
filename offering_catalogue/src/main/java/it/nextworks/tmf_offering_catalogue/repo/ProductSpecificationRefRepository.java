package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.product.ProductSpecificationRef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductSpecificationRefRepository extends JpaRepository<ProductSpecificationRef, String> {

    Optional<ProductSpecificationRef> findByHref(String href);

    Optional<ProductSpecificationRef> findById(String id);

    List<ProductSpecificationRef> findByName(String name);

    List<ProductSpecificationRef> findAll();
}
