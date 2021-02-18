package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.service.ServiceCandidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceCandidateRepository extends JpaRepository<ServiceCandidate, String> {

    Optional<ServiceCandidate> findByHref(String href);

    List<ServiceCandidate> findByName(String name);

    Optional<ServiceCandidate> findById(String uuid);

    List<ServiceCandidate> findAll();
}
