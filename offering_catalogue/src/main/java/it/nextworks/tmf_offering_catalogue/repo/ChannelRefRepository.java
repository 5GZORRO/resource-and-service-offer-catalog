package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.product.ChannelRef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRefRepository extends JpaRepository<ChannelRef, String> { }
