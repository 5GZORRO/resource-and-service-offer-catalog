package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.party.OrganizationRef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRefRepository extends JpaRepository<OrganizationRef, String> { }
