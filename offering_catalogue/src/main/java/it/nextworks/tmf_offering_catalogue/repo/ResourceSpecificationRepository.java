package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.resource.ResourceSpecification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceSpecificationRepository extends JpaRepository<ResourceSpecification, String> { }
