package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.resource.ResourceSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ResourceSpecificationRepository extends JpaRepository<ResourceSpecification, String> {

    Optional<ResourceSpecification> findByHref(String href);

    @Query("SELECT rs FROM ResourceSpecification rs WHERE rs.id = ?1")
    Optional<ResourceSpecification> findByResourceSpecificationId(String id);

    Optional<ResourceSpecification> findById(String uuid);

    List<ResourceSpecification> findByName(String name);

    List<ResourceSpecification> findAll();
}
