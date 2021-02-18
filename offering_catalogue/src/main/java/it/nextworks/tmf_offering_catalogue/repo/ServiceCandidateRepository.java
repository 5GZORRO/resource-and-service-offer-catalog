package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.service.ServiceCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ServiceCandidateRepository extends JpaRepository<ServiceCandidate, String> {

    Optional<ServiceCandidate> findByHref(String href);

    @Query("SELECT sc FROM ServiceCandidate sc WHERE sc.id = ?1")
    Optional<ServiceCandidate> findByServiceCandidateId(String id);

    List<ServiceCandidate> findByName(String name);

    Optional<ServiceCandidate> findById(String uuid);

    List<ServiceCandidate> findAll();
}
