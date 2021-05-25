package it.nextworks.tmf_offering_catalog.interfaces;

import org.springframework.http.ResponseEntity;

public interface GeographicAddressInterface {

    ResponseEntity<?> listGeographicAddress(String fields);

    ResponseEntity<?> retrieveGeographicAddress(String id, String fields);

}
