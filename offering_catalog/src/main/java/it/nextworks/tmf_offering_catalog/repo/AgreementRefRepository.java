package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.AgreementRef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgreementRefRepository extends JpaRepository<AgreementRef, String> { }
