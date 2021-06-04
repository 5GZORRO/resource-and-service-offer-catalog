package it.nextworks.tmf_offering_catalog.interfaces;

import com.fasterxml.jackson.databind.JsonNode;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOrderCreate;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOrderUpdate;
import org.springframework.http.ResponseEntity;

public interface ProductOrderInterface {

    ResponseEntity<?> createProductOrder(ProductOrderCreate productOrderCreate);

    ResponseEntity<?> deleteProductOrder(String id);

    ResponseEntity<?> listProductOrder(String fields, Integer limit, Integer offset);

    ResponseEntity<?> patchProductOrder(String id, ProductOrderUpdate productOrder);

    ResponseEntity<?> retrieveProductOrder(String id, String fields);

}
