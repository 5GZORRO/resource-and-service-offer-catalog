package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.party.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, String> {

    @Query("SELECT o FROM Organization o WHERE o.id = ?1")
    Optional<Organization> findByOrganizationId(String id);

    List<Organization> findAll();
}
