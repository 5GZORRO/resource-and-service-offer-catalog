package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.common.AttachmentRefOrValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRefOrValueRepository extends JpaRepository<AttachmentRefOrValue, String> { }
