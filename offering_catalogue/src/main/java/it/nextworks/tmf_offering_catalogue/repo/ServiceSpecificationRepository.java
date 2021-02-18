package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.service.ServiceSpecification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceSpecificationRepository extends JpaRepository<ServiceSpecification, String> {

    Optional<ServiceSpecification> findByHref(String href);

    Optional<ServiceSpecification> findById(String uuid);

    List<ServiceSpecification> findByName(String name);

    List<ServiceSpecification> findAll();
}
