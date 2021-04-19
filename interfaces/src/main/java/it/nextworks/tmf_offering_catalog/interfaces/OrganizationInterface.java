package it.nextworks.tmf_offering_catalog.interfaces;

import it.nextworks.tmf_offering_catalog.information_models.party.OrganizationCreateWrapper;
import it.nextworks.tmf_offering_catalog.information_models.party.OrganizationUpdate;
import org.springframework.http.ResponseEntity;

public interface OrganizationInterface {

    ResponseEntity<?> createOrganization(OrganizationCreateWrapper organizationCreateWrapper);

    ResponseEntity<?> deleteOrganization();

    ResponseEntity<?> patchOrganization(OrganizationUpdate organization);

    ResponseEntity<?> retrieveOrganization(String fields);
}
