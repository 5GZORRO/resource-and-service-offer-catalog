package it.nextworks.tmf_offering_catalog.repo;


import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductOrderStatusRepository extends JpaRepository<ProductOrderStatus, String> {

    @Query("SELECT pos FROM ProductOrderStatus pos WHERE pos.did = ?1")
    Optional<ProductOrderStatus> findByProductOrderStatusDid(String did);
}
