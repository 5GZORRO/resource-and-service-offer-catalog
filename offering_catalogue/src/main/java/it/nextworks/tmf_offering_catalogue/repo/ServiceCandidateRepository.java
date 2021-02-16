package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.service.ServiceCandidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceCandidateRepository extends JpaRepository<ServiceCandidate, String> { }
