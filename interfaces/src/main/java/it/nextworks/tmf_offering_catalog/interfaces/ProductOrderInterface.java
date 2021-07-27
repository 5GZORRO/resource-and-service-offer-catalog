package it.nextworks.tmf_offering_catalog.interfaces;

import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderCreate;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderUpdate;
import org.springframework.http.ResponseEntity;

public interface ProductOrderInterface {

    ResponseEntity<?> createProductOrder(ProductOrderCreate productOrderCreate);

    ResponseEntity<?> deleteProductOrder(String id);

    ResponseEntity<?> listProductOrder(String fields, Integer limit, Integer offset);

    ResponseEntity<?> patchProductOrder(String id, ProductOrderUpdate productOrder);

    ResponseEntity<?> retrieveProductOrder(String id, String fields);

}
