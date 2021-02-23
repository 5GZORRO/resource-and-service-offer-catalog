package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.BundledProductSpecification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BundledProductSpecificationRepository extends JpaRepository<BundledProductSpecification, String> { }
