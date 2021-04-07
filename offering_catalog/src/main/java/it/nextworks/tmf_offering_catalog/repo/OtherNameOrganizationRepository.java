package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.party.OtherNameOrganization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtherNameOrganizationRepository extends JpaRepository<OtherNameOrganization, String> { }
