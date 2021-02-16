package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.product.ProductOfferingTerm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOfferingTermRepository extends JpaRepository<ProductOfferingTerm, String> { }
