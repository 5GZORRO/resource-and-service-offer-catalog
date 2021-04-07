package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.party.OrganizationIdentification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationIdentificationRepository extends JpaRepository<OrganizationIdentification, String> { }
