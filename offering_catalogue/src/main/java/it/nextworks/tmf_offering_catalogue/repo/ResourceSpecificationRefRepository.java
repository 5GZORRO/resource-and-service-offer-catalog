package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.ResourceSpecificationRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ResourceSpecificationRefRepository extends JpaRepository<ResourceSpecificationRef, String> {

    List<ResourceSpecificationRef> findByHref(String href);

    @Query("SELECT rsr FROM ResourceSpecificationRef rsr WHERE rsr.id = ?1")
    List<ResourceSpecificationRef> findByResourceSpecificationId(String id);

    List<ResourceSpecificationRef> findByName(String name);

    Optional<ResourceSpecificationRef> findById(String uuid);

    List<ResourceSpecificationRef> findAll();
}
