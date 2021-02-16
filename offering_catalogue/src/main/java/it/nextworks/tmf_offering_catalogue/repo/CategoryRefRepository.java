package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.product.CategoryRef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRefRepository extends JpaRepository<CategoryRef, String> { }
