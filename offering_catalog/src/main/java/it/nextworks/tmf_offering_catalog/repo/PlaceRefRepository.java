package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.common.PlaceRef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRefRepository extends JpaRepository<PlaceRef, String> { }
