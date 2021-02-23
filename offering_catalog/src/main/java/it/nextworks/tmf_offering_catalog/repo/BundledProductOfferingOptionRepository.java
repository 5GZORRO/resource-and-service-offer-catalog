package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.BundledProductOfferingOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BundledProductOfferingOptionRepository extends JpaRepository<BundledProductOfferingOption, String> { }
