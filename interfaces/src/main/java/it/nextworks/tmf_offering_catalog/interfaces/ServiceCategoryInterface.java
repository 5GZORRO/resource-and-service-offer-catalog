package it.nextworks.tmf_offering_catalog.interfaces;

import it.nextworks.tmf_offering_catalog.information_models.service.ServiceCategory;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceCategoryCreate;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceCategoryUpdate;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ServiceCategoryInterface {

    ResponseEntity<ServiceCategory> createServiceCategory(ServiceCategoryCreate serviceCategory);

    ResponseEntity<Void> deleteServiceCategory(String id);

    ResponseEntity<List<ServiceCategory>> listServiceCategory(String fields, Integer limit, Integer offset);

    ResponseEntity<ServiceCategory> patchServiceCategory(String id, ServiceCategoryUpdate serviceCategory);

    ResponseEntity<ServiceCategory> retrieveServiceCategory(String id, String fields);
}
