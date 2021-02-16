package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.ServiceCandidateRef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceCandidateRefRepository extends JpaRepository<ServiceCandidateRef, String> { }
