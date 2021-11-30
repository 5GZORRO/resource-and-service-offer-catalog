package it.nextworks.tmf_offering_catalog.interfaces;

import org.springframework.http.ResponseEntity;

public interface GeographicAddressInterface {

    ResponseEntity<?> retrieveGeographicAddress(String id, String fields);
    ResponseEntity<?> retrieveGeographicAddressByCoo(String x, String y);
}
