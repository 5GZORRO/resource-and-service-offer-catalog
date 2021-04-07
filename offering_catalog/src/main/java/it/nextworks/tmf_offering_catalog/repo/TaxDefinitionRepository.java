package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.party.TaxDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxDefinitionRepository extends JpaRepository<TaxDefinition, String> { }
