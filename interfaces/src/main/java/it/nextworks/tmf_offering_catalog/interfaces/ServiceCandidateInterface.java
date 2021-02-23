package it.nextworks.tmf_offering_catalog.interfaces;

import it.nextworks.tmf_offering_catalog.information_models.service.ServiceCandidate;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceCandidateCreate;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceCandidateUpdate;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ServiceCandidateInterface {

    ResponseEntity<ServiceCandidate> createServiceCandidate(ServiceCandidateCreate serviceCandidate);

    ResponseEntity<Void> deleteServiceCandidate(String id);

    ResponseEntity<List<ServiceCandidate>> listServiceCandidate(String fields, Integer limit, Integer offset);

    ResponseEntity<ServiceCandidate> patchServiceCandidate(String id, ServiceCandidateUpdate serviceCandidate);

    ResponseEntity<ServiceCandidate> retrieveServiceCandidate(String id, String fields);
}
