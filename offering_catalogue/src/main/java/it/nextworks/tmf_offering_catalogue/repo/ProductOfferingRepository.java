package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.product.ProductOffering;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOfferingRepository extends JpaRepository<ProductOffering, String> { }
