package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.kafka.ExternalProductSpecification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExternalProductSpecificationRepository extends JpaRepository<ExternalProductSpecification, String> {

    Optional<ExternalProductSpecification> findById(String id);

    List<ExternalProductSpecification> findAll();
}
