package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.service.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, String> { }
