package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.ResourceSpecificationRef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResourceSpecificationRefRepository extends JpaRepository<ResourceSpecificationRef, String> {

    List<ResourceSpecificationRef> findByHref(String href);

    List<ResourceSpecificationRef> findByName(String name);

    Optional<ResourceSpecificationRef> findById(String uuid);

    List<ResourceSpecificationRef> findAll();
}
