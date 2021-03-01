package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.service.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, String> {

    @Query("SELECT sc FROM ServiceCategory sc WHERE sc.id = ?1")
    Optional<ServiceCategory> findByServiceCategoryId(String id);

    List<ServiceCategory> findAll();
}
