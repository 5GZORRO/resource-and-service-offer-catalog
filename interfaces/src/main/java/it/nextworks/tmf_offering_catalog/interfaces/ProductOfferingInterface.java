package it.nextworks.tmf_offering_catalog.interfaces;

import it.nextworks.tmf_offering_catalog.information_models.product.ProductOffering;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingCreate;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingUpdate;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductOfferingInterface {

    ResponseEntity<ProductOffering> createProductOffering(ProductOfferingCreate productOffering);

    ResponseEntity<Void> deleteProductOffering(String id);

    ResponseEntity<List<ProductOffering>> listProductOffering(String fields, Integer limit, Integer offset);

    ResponseEntity<ProductOffering> patchProductOffering(String id, ProductOfferingUpdate productOffering);

    ResponseEntity<ProductOffering> retrieveProductOffering(String id, String fields);
}
