package it.nextworks.tmf_offering_catalog.interfaces;

import it.nextworks.tmf_offering_catalog.information_models.product.Category;
import it.nextworks.tmf_offering_catalog.information_models.product.CategoryCreate;
import it.nextworks.tmf_offering_catalog.information_models.product.CategoryUpdate;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryInterface {

    ResponseEntity<Category> createCategory(CategoryCreate category);

    ResponseEntity<Void> deleteCategory(String id);

    ResponseEntity<List<Category>> listCategory(String fields, Integer limit, Integer offset);

    ResponseEntity<Category> patchCategory(CategoryUpdate category, String id);

    ResponseEntity<Category> retrieveCategory(String id, String fields);
}
