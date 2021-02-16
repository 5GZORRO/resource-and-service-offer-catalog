package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.product.MarketSegmentRef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketSegmentRefRepository extends JpaRepository<MarketSegmentRef, String> { }
