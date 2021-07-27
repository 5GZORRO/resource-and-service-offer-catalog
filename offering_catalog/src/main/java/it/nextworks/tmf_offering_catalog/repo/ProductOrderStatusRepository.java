package it.nextworks.tmf_offering_catalog.repo;


import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderStatusRepository extends JpaRepository<ProductOrderStatus, String> {
}
