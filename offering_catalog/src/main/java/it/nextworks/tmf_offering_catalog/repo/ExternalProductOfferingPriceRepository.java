package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.kafka.ExternalProductOfferingPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExternalProductOfferingPriceRepository extends JpaRepository<ExternalProductOfferingPrice, String> {

    Optional<ExternalProductOfferingPrice> findById(String id);

    List<ExternalProductOfferingPrice> findAll();
}
