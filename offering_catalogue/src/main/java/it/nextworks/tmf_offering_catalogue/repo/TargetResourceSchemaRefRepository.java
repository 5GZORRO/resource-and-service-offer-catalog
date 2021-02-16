package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.resource.TargetResourceSchemaRef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TargetResourceSchemaRefRepository extends JpaRepository<TargetResourceSchemaRef, String> { }
