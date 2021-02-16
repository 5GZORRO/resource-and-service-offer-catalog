package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.service.ServiceCategoryRef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceCategoryRefRepository extends JpaRepository<ServiceCategoryRef, String> { }
