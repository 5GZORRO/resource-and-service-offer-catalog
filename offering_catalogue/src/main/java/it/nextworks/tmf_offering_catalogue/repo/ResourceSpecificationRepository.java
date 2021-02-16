package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.resource.ResourceSpecification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResourceSpecificationRepository extends JpaRepository<ResourceSpecification, String> {

    Optional<ResourceSpecification> findByHref(String href);

    Optional<ResourceSpecification> findById(String id);

    List<ResourceSpecification> findByName(String name);

    List<ResourceSpecification> findAll();
}
