package it.nextworks.tmf_offering_catalog.interfaces;

import it.nextworks.tmf_offering_catalog.information_models.service.ServiceCategoryCreate;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceCategoryUpdate;
import org.springframework.http.ResponseEntity;

public interface ServiceCategoryInterface {

    ResponseEntity<?> createServiceCategory(ServiceCategoryCreate serviceCategory);

    ResponseEntity<?> deleteServiceCategory(String id);

    ResponseEntity<?> listServiceCategory(String fields, Integer limit, Integer offset);

    ResponseEntity<?> patchServiceCategory(String id, ServiceCategoryUpdate serviceCategory);

    ResponseEntity<?> retrieveServiceCategory(String id, String fields);
}
