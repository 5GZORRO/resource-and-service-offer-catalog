package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.ServiceSpecificationRef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceSpecificationRefRepository extends JpaRepository<ServiceSpecificationRef, String> {

    List<ServiceSpecificationRef> findByHref(String href);

    List<ServiceSpecificationRef> findByName(String name);

    Optional<ServiceSpecificationRef> findById(String uuid);

    List<ServiceSpecificationRef> findAll();
}
