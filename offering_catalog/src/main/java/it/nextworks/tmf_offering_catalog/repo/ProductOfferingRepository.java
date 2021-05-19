package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.ProductOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductOfferingRepository
        extends JpaRepository<ProductOffering, String>, ProductOfferingRepositoryCustom {

    @Query("SELECT po FROM ProductOffering po WHERE po.id = ?1")
    Optional<ProductOffering> findByProductOfferingId(String id);

    List<ProductOffering> findAll();
}
