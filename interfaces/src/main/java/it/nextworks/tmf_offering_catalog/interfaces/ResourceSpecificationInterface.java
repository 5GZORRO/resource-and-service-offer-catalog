package it.nextworks.tmf_offering_catalog.interfaces;

import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceSpecification;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceSpecificationCreate;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceSpecificationUpdate;
import org.springframework.http.ResponseEntity;
import org.threeten.bp.OffsetDateTime;

import java.util.List;

public interface ResourceSpecificationInterface {

    ResponseEntity<ResourceSpecification> createResourceSpecification(ResourceSpecificationCreate serviceSpecification);

    ResponseEntity<Void> deleteResourceSpecification(String id);

    ResponseEntity<List<ResourceSpecification>> listResourceSpecification(String baseType,
                                                                          String schemaLocation,
                                                                          String type,
                                                                          String attachmentType,
                                                                          String attachmentMimeType,
                                                                          String attachmentName,
                                                                          String attachmentUri,
                                                                          String category,
                                                                          String featureType,
                                                                          Boolean featureIsBundle,
                                                                          Boolean featureIsEnabled,
                                                                          String featureName,
                                                                          String featureVersion,
                                                                          String fields,
                                                                          Boolean isBundle,
                                                                          OffsetDateTime lastUpdate,
                                                                          String lifecycleStatus,
                                                                          String name,
                                                                          String relatedPartyName,
                                                                          String relatedPartyRole,
                                                                          String resourceSpecCharacteristicSchemaLocation,
                                                                          String resourceSpecCharacteristicType,
                                                                          String resourceSpecCharacteristicValueSchemaLocation,
                                                                          Boolean resourceSpecCharacteristicConfigurable,
                                                                          Boolean resourceSpecCharacteristicExtensible,
                                                                          Boolean resourceSpecCharacteristicIsUnique,
                                                                          Integer resourceSpecCharacteristicMaxCardinality,
                                                                          Integer resourceSpecCharacteristicMinCardinality,
                                                                          String resourceSpecCharacteristicName,
                                                                          String resourceSpecCharacteristicRegex,
                                                                          String resourceSpecCharacteristicValueType,
                                                                          String resourceSpecRelationshipName,
                                                                          String resourceSpecRelationshipRole,
                                                                          String resourceSpecRelationshipType,
                                                                          String targetResourceSchemaSchemaLocation,
                                                                          String targetResourceSchemaType,
                                                                          OffsetDateTime validForEndDateTime,
                                                                          OffsetDateTime validForStartDateTime,
                                                                          String version);

    ResponseEntity<ResourceSpecification> patchResourceSpecification(String id,
                                                                     ResourceSpecificationUpdate serviceSpecification);

    ResponseEntity<List<ResourceSpecification>> retrieveResourceSpecification(String id);
}
