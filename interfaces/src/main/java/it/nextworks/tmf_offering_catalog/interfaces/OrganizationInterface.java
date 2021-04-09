package it.nextworks.tmf_offering_catalog.interfaces;

import it.nextworks.tmf_offering_catalog.information_models.party.Organization;
import it.nextworks.tmf_offering_catalog.information_models.party.OrganizationCreate;
import it.nextworks.tmf_offering_catalog.information_models.party.OrganizationUpdate;
import org.springframework.http.ResponseEntity;

public interface OrganizationInterface {

    ResponseEntity<?> createOrganization(OrganizationCreate organization);

    ResponseEntity<?> deleteOrganization(String id);

    ResponseEntity<?> patchOrganization(String id, OrganizationUpdate organization);

    ResponseEntity<?> retrieveOrganization(String id, String fields);
}
