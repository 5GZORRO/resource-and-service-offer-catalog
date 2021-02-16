package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.product.TaxItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxItemRepository extends JpaRepository<TaxItem, String> { }
