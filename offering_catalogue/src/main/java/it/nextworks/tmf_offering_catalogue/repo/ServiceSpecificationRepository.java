package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.service.ServiceSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ServiceSpecificationRepository extends JpaRepository<ServiceSpecification, String> {

    Optional<ServiceSpecification> findByHref(String href);

    @Query("SELECT ss FROM ServiceSpecification ss WHERE ss.id = ?1")
    Optional<ServiceSpecification> findByServiceSpecificationId(String id);

    Optional<ServiceSpecification> findById(String uuid);

    List<ServiceSpecification> findByName(String name);

    List<ServiceSpecification> findAll();
}
