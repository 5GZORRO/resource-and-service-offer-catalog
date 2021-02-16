package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.product.CategoryRef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRefRepository extends JpaRepository<CategoryRef, String> {

    Optional<CategoryRef> findByHref(String href);

    Optional<CategoryRef> findById(String id);

    List<CategoryRef> findByName(String name);

    List<CategoryRef> findAll();
}
