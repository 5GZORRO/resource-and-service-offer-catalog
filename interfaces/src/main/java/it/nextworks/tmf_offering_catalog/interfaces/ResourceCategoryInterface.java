package it.nextworks.tmf_offering_catalog.interfaces;

import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceCategory;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceCategoryCreate;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceCategoryUpdate;
import org.springframework.http.ResponseEntity;
import org.threeten.bp.OffsetDateTime;

import java.util.List;

public interface ResourceCategoryInterface {

    ResponseEntity<ResourceCategory> createResourceCategory(ResourceCategoryCreate resCategory);

    ResponseEntity<Void> deleteResourceCategory(String id);

    ResponseEntity<List<ResourceCategory>> listResourceCategory(String baseType,
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

    ResponseEntity<ResourceCategory> patchResourceCategory(String id, ResourceCategoryUpdate resourceCategory);

    ResponseEntity<List<ResourceCategory>> retrieveResourceCategory(String id);
}
