package it.nextworks.tmf_offering_catalog.interfaces;

import it.nextworks.tmf_offering_catalog.information_models.service.ServiceSpecification;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceSpecificationCreate;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceSpecificationUpdate;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ServiceSpecificationInterface {

    ResponseEntity<ServiceSpecification> createServiceSpecification(ServiceSpecificationCreate serviceSpecification);

    ResponseEntity<Void> deleteServiceSpecification(String id);

    ResponseEntity<List<ServiceSpecification>> listServiceSpecification(String fields, Integer limit, Integer offset);

    ResponseEntity<ServiceSpecification> patchServiceSpecification(String id,
                                                                            ServiceSpecificationUpdate serviceSpecification);

    ResponseEntity<ServiceSpecification> retrieveServiceSpecification(String id, String fields);
}
