package it.nextworks.tmf_offering_catalog.interfaces;

import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddressValidationCreate;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddressValidationUpdate;
import org.springframework.http.ResponseEntity;

public interface GeographicAddressValidationInterface {

    ResponseEntity<?> createGeographicAddressValidation(GeographicAddressValidationCreate geographicAddressValidation);

    ResponseEntity<?> listGeographicAddressValidation(String fields);

    ResponseEntity<?> patchGeographicAddressValidation(String id, GeographicAddressValidationUpdate geographicAddressValidation);

    ResponseEntity<?> retrieveGeographicAddressValidation(String id, String fields);

}
