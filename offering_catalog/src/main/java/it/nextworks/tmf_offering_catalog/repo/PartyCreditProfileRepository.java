package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.party.PartyCreditProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyCreditProfileRepository extends JpaRepository<PartyCreditProfile, String> { }
