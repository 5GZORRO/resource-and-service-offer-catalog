package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.kafka.ExternalResourceSpecification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExternalResourceSpecificationRepository extends JpaRepository<ExternalResourceSpecification, String> {

    Optional<ExternalResourceSpecification> findById(String id);

    List<ExternalResourceSpecification> findAll();
}
