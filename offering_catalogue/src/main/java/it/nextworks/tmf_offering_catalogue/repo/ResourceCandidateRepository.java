package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.resource.ResourceCandidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResourceCandidateRepository extends JpaRepository<ResourceCandidate, String> {

    Optional<ResourceCandidate> findByHref(String href);

    Optional<ResourceCandidate> findById(String id);

    List<ResourceCandidate> findByName(String name);

    Optional<ResourceCandidate> findByUuid(String uuid);

    List<ResourceCandidate> findAll();
}
