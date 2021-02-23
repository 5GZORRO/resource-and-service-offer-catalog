package it.nextworks.tmf_offering_catalog.interfaces;

import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceCandidate;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceCandidateCreate;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceCandidateUpdate;
import org.springframework.http.ResponseEntity;
import org.threeten.bp.OffsetDateTime;

import java.util.List;

public interface ResourceCandidateInterface {

    ResponseEntity<ResourceCandidate> createResourceCandidate(ResourceCandidateCreate resourceCandidate);

    ResponseEntity<Void> deleteResourceCandidate(String id);

    ResponseEntity<List<ResourceCandidate>> listResourceCandidate(String baseType,
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

    ResponseEntity<ResourceCandidate> patchResourceCandidate(String id, ResourceCandidateUpdate resourceCandidate);

    ResponseEntity<List<ResourceCandidate>> retrieveResourceCandidate(String id);
}
