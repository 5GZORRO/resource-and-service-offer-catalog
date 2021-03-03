package it.nextworks.tmf_offering_catalog.interfaces;

import it.nextworks.tmf_offering_catalog.information_models.service.ServiceCandidateCreate;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceCandidateUpdate;
import org.springframework.http.ResponseEntity;

public interface ServiceCandidateInterface {

    ResponseEntity<?> createServiceCandidate(ServiceCandidateCreate serviceCandidate);

    ResponseEntity<?> deleteServiceCandidate(String id);

    ResponseEntity<?> listServiceCandidate(String fields, Integer limit, Integer offset);

    ResponseEntity<?> patchServiceCandidate(String id, ServiceCandidateUpdate serviceCandidate);

    ResponseEntity<?> retrieveServiceCandidate(String id, String fields);
}
