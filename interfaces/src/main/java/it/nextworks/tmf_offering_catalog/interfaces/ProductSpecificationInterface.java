package it.nextworks.tmf_offering_catalog.interfaces;

import it.nextworks.tmf_offering_catalog.information_models.product.ProductSpecificationCreate;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductSpecificationUpdate;
import org.springframework.http.ResponseEntity;

public interface ProductSpecificationInterface {

    ResponseEntity<?> createProductSpecification(ProductSpecificationCreate productSpecification);

    ResponseEntity<?> deleteProductSpecification(String id);

    ResponseEntity<?> listProductSpecification(String fields, Integer limit, Integer offset);

    ResponseEntity<?> patchProductSpecification(String id,
                                                                   ProductSpecificationUpdate productSpecification);

    ResponseEntity<?> retrieveProductSpecification(String id, String fields);
}
