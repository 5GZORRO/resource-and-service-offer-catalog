package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.party.OrganizationParentRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationParentRelationshipRepository extends JpaRepository<OrganizationParentRelationship, String> { }
