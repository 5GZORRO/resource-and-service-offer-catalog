package it.nextworks.tmf_offering_catalog.interfaces;

import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingCreate;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingUpdate;
import org.springframework.http.ResponseEntity;

public interface ProductOfferingInterface {

    ResponseEntity<?> createProductOffering(ProductOfferingCreate productOffering);

    ResponseEntity<?> deleteProductOffering(String id);

    ResponseEntity<?> listProductOffering(String fields, Integer limit, Integer offset);

    ResponseEntity<?> patchProductOffering(String id, ProductOfferingUpdate productOffering);

    ResponseEntity<?> retrieveProductOffering(String id, String fields);
}
