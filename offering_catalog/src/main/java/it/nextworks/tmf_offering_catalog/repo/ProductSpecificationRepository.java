package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.ProductSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductSpecificationRepository extends JpaRepository<ProductSpecification, String> {

    @Query("SELECT ps FROM ProductSpecification ps WHERE ps.id = ?1")
    Optional<ProductSpecification> findByProductSpecificationId(String id);

    List<ProductSpecification> findAll();
}
