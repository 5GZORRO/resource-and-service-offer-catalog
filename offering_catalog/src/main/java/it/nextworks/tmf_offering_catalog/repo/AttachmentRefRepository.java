package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.common.AttachmentRef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRefRepository extends JpaRepository<AttachmentRef, String> { }
