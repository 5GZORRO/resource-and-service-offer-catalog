package it.nextworks.tmf_offering_catalog.interfaces;

import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingPriceCreate;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingPriceUpdate;
import org.springframework.http.ResponseEntity;

public interface ProductOfferingPriceInterface {

    ResponseEntity<?> createProductOfferingPrice(ProductOfferingPriceCreate productOfferingPrice);

    ResponseEntity<?> deleteProductOfferingPrice(String id);

    ResponseEntity<?> listProductOfferingPrice(String fields, Integer limit, Integer offset);

    ResponseEntity<?> patchProductOfferingPrice(String id,
                                                                   ProductOfferingPriceUpdate productOfferingPrice);

    ResponseEntity<?> retrieveProductOfferingPrice(String id, String fields);
}
