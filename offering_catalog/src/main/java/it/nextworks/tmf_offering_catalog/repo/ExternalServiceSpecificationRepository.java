package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.kafka.ExternalServiceSpecification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExternalServiceSpecificationRepository extends JpaRepository<ExternalServiceSpecification, String> {

    Optional<ExternalServiceSpecification> findById(String id);

    List<ExternalServiceSpecification> findAll();
}
