package it.nextworks.tmf_offering_catalog.interfaces;

import org.springframework.http.ResponseEntity;

public interface GeographicAddressInterface {

    ResponseEntity<?> listGeographicAddress(String fields, Integer offset, Integer limit);

    ResponseEntity<?> listGeographicSubAddress(String geographicAddressId, String fields, Integer offset, Integer limit);

    ResponseEntity<?> retrieveGeographicAddress(String id, String fields);

    ResponseEntity<?> retrieveGeographicSubAddress(String geographicAddressId, String id, String fields);

}
