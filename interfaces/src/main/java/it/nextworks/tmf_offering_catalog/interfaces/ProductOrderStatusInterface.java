package it.nextworks.tmf_offering_catalog.interfaces;

import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderStatesEnum;
import org.springframework.http.ResponseEntity;

public interface ProductOrderStatusInterface {
    ResponseEntity<?> retrieveProductOrderStatus(String id);
    ResponseEntity<?> updateProductOrderStatus(String id, String instanceId);
}
