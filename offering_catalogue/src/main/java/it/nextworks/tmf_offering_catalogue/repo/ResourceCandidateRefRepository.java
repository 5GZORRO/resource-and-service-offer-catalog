package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.ResourceCandidateRef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResourceCandidateRefRepository extends JpaRepository<ResourceCandidateRef, String> {

    Optional<ResourceCandidateRef> findByHref(String href);

    Optional<ResourceCandidateRef> findById(String id);

    List<ResourceCandidateRef> findByName(String name);

    List<ResourceCandidateRef> findAll();
}
