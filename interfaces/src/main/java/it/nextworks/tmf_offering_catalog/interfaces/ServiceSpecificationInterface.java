package it.nextworks.tmf_offering_catalog.interfaces;

import it.nextworks.tmf_offering_catalog.information_models.service.ServiceSpecificationCreate;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceSpecificationUpdate;
import org.springframework.http.ResponseEntity;

public interface ServiceSpecificationInterface {

    ResponseEntity<?> createServiceSpecification(ServiceSpecificationCreate serviceSpecification);

    ResponseEntity<?> deleteServiceSpecification(String id);

    ResponseEntity<?> listServiceSpecification(String fields, Integer limit, Integer offset);

    ResponseEntity<?> patchServiceSpecification(String id,
                                                                            ServiceSpecificationUpdate serviceSpecification);

    ResponseEntity<?> retrieveServiceSpecification(String id, String fields);
}
