package it.nextworks.tmf_offering_catalog.interfaces;

import it.nextworks.tmf_offering_catalog.information_models.product.ProductSpecification;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductSpecificationCreate;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductSpecificationUpdate;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductSpecificationInterface {

    ResponseEntity<ProductSpecification> createProductSpecification(ProductSpecificationCreate productSpecification);

    ResponseEntity<Void> deleteProductSpecification(String id);

    ResponseEntity<List<ProductSpecification>> listProductSpecification(String fields, Integer limit, Integer offset);

    ResponseEntity<ProductSpecification> patchProductSpecification(String id,
                                                                   ProductSpecificationUpdate productSpecification);

    ResponseEntity<ProductSpecification> retrieveProductSpecification(String id, String fields);
}
