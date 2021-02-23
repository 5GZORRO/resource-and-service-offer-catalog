package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.TaxItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxItemRepository extends JpaRepository<TaxItem, String> { }
