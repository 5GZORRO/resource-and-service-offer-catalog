package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.resource.ResourceCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ResourceCandidateRepository extends JpaRepository<ResourceCandidate, String> {

    Optional<ResourceCandidate> findByHref(String href);

    @Query("SELECT rc FROM ResourceCandidate rc WHERE rc.id = ?1")
    Optional<ResourceCandidate> findByResourceCandidateId(String id);

    List<ResourceCandidate> findByName(String name);

    Optional<ResourceCandidate> findById(String uuid);

    List<ResourceCandidate> findAll();
}
