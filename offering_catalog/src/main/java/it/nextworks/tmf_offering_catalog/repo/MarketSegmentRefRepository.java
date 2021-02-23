package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.MarketSegmentRef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketSegmentRefRepository extends JpaRepository<MarketSegmentRef, String> { }
