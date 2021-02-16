package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.product.AttachmentRefOrValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRefOrValueRepository extends JpaRepository<AttachmentRefOrValue, String> { }
