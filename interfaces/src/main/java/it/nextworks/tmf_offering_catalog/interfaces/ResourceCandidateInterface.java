package it.nextworks.tmf_offering_catalog.interfaces;

import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceCandidateCreate;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceCandidateUpdate;
import org.springframework.http.ResponseEntity;
import org.threeten.bp.OffsetDateTime;

public interface ResourceCandidateInterface {

    ResponseEntity<?> createResourceCandidate(ResourceCandidateCreate resourceCandidate);

    ResponseEntity<?> deleteResourceCandidate(String id);

    ResponseEntity<?> listResourceCandidate(String baseType,
                                                                  String schemaLocation,
                                                                  String type,
                                                                  String categoryType,
                                                                  String categoryName,
                                                                  String categoryVersion,
                                                                  String fields,
                                                                  OffsetDateTime lastUpdate,
                                                                  String lifecycleStatus,
                                                                  String name,
                                                                  String resourceSpecificationType,
                                                                  String resourceSpecificationName,
                                                                  String resourceSpecificationVersion,
                                                                  OffsetDateTime validForEndDateTime,
                                                                  OffsetDateTime validForStartDateTime,
                                                                  String version);

    ResponseEntity<?> patchResourceCandidate(String id, ResourceCandidateUpdate resourceCandidate);

    ResponseEntity<?> retrieveResourceCandidate(String id);
}
