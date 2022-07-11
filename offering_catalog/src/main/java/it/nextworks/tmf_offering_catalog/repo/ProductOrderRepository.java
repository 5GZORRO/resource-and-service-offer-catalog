package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, String> {

    @Query("SELECT po FROM ProductOrder po WHERE po.id = ?1")
    Optional<ProductOrder> findByProductOrderId(String id);

}
