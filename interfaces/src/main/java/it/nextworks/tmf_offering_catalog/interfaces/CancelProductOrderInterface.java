package it.nextworks.tmf_offering_catalog.interfaces;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;

public interface CancelProductOrderInterface {

    ResponseEntity<?> createCancelProductOrder();

    ResponseEntity<?> handleDIDReceiving(String id, JsonNode jsonNode);

    ResponseEntity<?> deleteCancelProductOrder();

    ResponseEntity<?> retrieveCancelProductOrder();

}
