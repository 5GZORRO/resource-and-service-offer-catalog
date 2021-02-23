package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.PricingLogicAlgorithm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricingLogicAlgorithmRepository extends JpaRepository<PricingLogicAlgorithm, String> { }
