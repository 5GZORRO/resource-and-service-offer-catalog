package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.product.SLARef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SLARefRepository extends JpaRepository<SLARef, String> {

    Optional<SLARef> findByHref(String href);

    Optional<SLARef> findById(String id);

    List<SLARef> findByName(String name);

    List<SLARef> findAll();
}
