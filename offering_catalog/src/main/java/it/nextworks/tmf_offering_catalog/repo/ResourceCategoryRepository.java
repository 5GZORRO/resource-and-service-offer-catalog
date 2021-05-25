package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ResourceCategoryRepository extends JpaRepository<ResourceCategory, String> {

    @Query("SELECT rc FROM ResourceCategory rc WHERE rc.id = ?1")
    Optional<ResourceCategory> findByResourceCategoryId(String id);

    List<ResourceCategory> findAll();

    Optional<ResourceCategory> findByName(String name);
}
