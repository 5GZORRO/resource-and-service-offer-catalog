package it.nextworks.tmf_offering_catalog.interfaces;

import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingPrice;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingPriceCreate;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingPriceUpdate;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductOfferingPriceInterface {

    ResponseEntity<ProductOfferingPrice> createProductOfferingPrice(ProductOfferingPriceCreate productOfferingPrice);

    ResponseEntity<Void> deleteProductOfferingPrice(String id);

    ResponseEntity<List<ProductOfferingPrice>> listProductOfferingPrice(String fields, Integer limit, Integer offset);

    ResponseEntity<ProductOfferingPrice> patchProductOfferingPrice(String id,
                                                                   ProductOfferingPriceUpdate productOfferingPrice);

    ResponseEntity<ProductOfferingPrice> retrieveProductOfferingPrice(String id, String fields);
}
