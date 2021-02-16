package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.resource.ResourceCategoryRef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceCategoryRefRepository extends JpaRepository<ResourceCategoryRef, String> { }
