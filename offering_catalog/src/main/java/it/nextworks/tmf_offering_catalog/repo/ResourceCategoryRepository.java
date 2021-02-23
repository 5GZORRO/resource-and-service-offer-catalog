package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceCategoryRepository extends JpaRepository<ResourceCategory, String> { }
