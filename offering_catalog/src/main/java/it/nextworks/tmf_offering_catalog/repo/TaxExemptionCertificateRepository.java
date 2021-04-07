package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.party.TaxExemptionCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxExemptionCertificateRepository extends JpaRepository<TaxExemptionCertificate, String> { }
