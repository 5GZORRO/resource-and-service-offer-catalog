package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.product.BundledProductOffering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BundledProductOfferingRepository extends JpaRepository<BundledProductOffering, String> {

    Optional<BundledProductOffering> findByHref(String href);

    Optional<BundledProductOffering> findById(String id);

    List<BundledProductOffering> findByName(String name);

    List<BundledProductOffering> findAll();
}
