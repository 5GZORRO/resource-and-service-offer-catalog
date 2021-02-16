package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.Quantity;
import it.nextworks.tmf_offering_catalogue.information_models.TimePeriod;
import it.nextworks.tmf_offering_catalogue.information_models.product.ProductOfferingTerm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductOfferingTermRepository extends JpaRepository<ProductOfferingTerm, String> {

    List<ProductOfferingTerm> findByDescription(String description);

    List<ProductOfferingTerm> findByDuration(Quantity duration);

    Optional<ProductOfferingTerm> findById(String id);

    List<ProductOfferingTerm> findByName(String name);

    List<ProductOfferingTerm> findByValidFor(TimePeriod validFor);

    List<ProductOfferingTerm> findAll();
}
