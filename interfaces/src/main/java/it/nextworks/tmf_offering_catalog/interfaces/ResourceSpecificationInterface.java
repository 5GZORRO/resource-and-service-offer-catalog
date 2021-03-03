package it.nextworks.tmf_offering_catalog.interfaces;

import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceSpecificationCreate;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceSpecificationUpdate;
import org.springframework.http.ResponseEntity;
import org.threeten.bp.OffsetDateTime;

public interface ResourceSpecificationInterface {

    ResponseEntity<?> createResourceSpecification(ResourceSpecificationCreate serviceSpecification);

    ResponseEntity<?> deleteResourceSpecification(String id);

    ResponseEntity<?> listResourceSpecification(String baseType,
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

    ResponseEntity<?> patchResourceSpecification(String id,
                                                                     ResourceSpecificationUpdate serviceSpecification);

    ResponseEntity<?> retrieveResourceSpecification(String id);
}
