package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.BundledProductOffering;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BundledProductOfferingRepository extends JpaRepository<BundledProductOffering, String> { }
