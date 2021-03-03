package it.nextworks.tmf_offering_catalog.interfaces;

import it.nextworks.tmf_offering_catalog.information_models.product.CategoryCreate;
import it.nextworks.tmf_offering_catalog.information_models.product.CategoryUpdate;
import org.springframework.http.ResponseEntity;

public interface CategoryInterface {

    ResponseEntity<?> createCategory(CategoryCreate category);

    ResponseEntity<?> deleteCategory(String id);

    ResponseEntity<?> listCategory(String fields, Integer limit, Integer offset);

    ResponseEntity<?> patchCategory(CategoryUpdate category, String id);

    ResponseEntity<?> retrieveCategory(String id, String fields);
}
