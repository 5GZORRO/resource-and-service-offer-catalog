package it.nextworks.tmf_offering_catalog.interfaces;

import org.springframework.http.ResponseEntity;

public interface ProductOfferingStatusInterface {
    ResponseEntity<?> retrieveProductOfferingStatus(String id);
}
