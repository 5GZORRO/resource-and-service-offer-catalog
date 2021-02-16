package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.product.BundledProductOfferingPriceRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BundledProductOfferingPriceRelationshipRepository
        extends JpaRepository<BundledProductOfferingPriceRelationship, String> { }
