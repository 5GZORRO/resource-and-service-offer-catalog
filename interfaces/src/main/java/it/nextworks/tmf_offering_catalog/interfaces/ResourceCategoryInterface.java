package it.nextworks.tmf_offering_catalog.interfaces;

import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceCategoryCreate;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceCategoryUpdate;
import org.springframework.http.ResponseEntity;
import org.threeten.bp.OffsetDateTime;

public interface ResourceCategoryInterface {

    ResponseEntity<?> createResourceCategory(ResourceCategoryCreate resCategory);

    ResponseEntity<?> deleteResourceCategory(String id);

    ResponseEntity<?> listResourceCategory(String baseType,
                                                                String schemalLocation,
                                                                String type,
                                                                String categoryType,
                                                                String categoryName,
                                                                String categoryVersion,
                                                                String fields,
                                                                Boolean isRoot,
                                                                OffsetDateTime lastUpdate,
                                                                String lifecycleStatus,
                                                                String name,
                                                                String parentId,
                                                                String relatedPartyName,
                                                                String relatedPartyRole,
                                                                String resourceCandidateType,
                                                                String resourceCandidateName,
                                                                String resourceCandidateVersion,
                                                                OffsetDateTime validForEndDateTime,
                                                                OffsetDateTime validForStartDateTime,
                                                                String version);

    ResponseEntity<?> findResourceCategoryByName(String name);

    ResponseEntity<?> patchResourceCategory(String id, ResourceCategoryUpdate resourceCategory);

    ResponseEntity<?> retrieveResourceCategory(String id);
}
