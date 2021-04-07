package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.party.OrganizationChildRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationChildRelationshipRepository extends JpaRepository<OrganizationChildRelationship, String> { }
