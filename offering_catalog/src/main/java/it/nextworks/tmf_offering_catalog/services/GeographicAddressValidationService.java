package it.nextworks.tmf_offering_catalog.services;

import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddressValidation;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddressValidationCreate;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddressValidationUpdate;
import it.nextworks.tmf_offering_catalog.repo.GeographicAddressValidationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GeographicAddressValidationService {

    private static final Logger log = LoggerFactory.getLogger(GeographicAddressValidationService.class);

    private static final String protocol = "http://";
    @Value("${server.hostname}")
    private String hostname;
    @Value("${server.port}")
    private String port;
    private static final String path = "/tmf-api/geographicAddressManagement/v4/geographicAddressValidation/";

    @Autowired
    private GeographicAddressValidationRepository geographicAddressValidationRepository;

    @Transactional
    public GeographicAddressValidation create(GeographicAddressValidationCreate geographicAddressValidationCreate) {
        log.info("Received request to create a Geographic Address.");
        GeographicAddressValidation geographicAddressValidation = createAndPopulateGeographicAddressValidation(geographicAddressValidationCreate);
        geographicAddressValidationRepository.save(geographicAddressValidation);
        log.info("Geographic Address Validation created with id " + geographicAddressValidation.getId() + ".");
        return geographicAddressValidation;
    }

    @Transactional
    public List<GeographicAddressValidation> list() {
        log.info("Received request to retrieve all Geographic Addresses");
        return geographicAddressValidationRepository.findAll();
    }

    @Transactional
    public GeographicAddressValidation get(String id) throws NotExistingEntityException {
        log.info("Received request to retrieve Geographic Address Validation with id " + id + ".");
        Optional<GeographicAddressValidation> retrieved = geographicAddressValidationRepository.findByGeographicAddressValidationId(id);
        if (!retrieved.isPresent()) {
            throw new NotExistingEntityException("Geographic Address Validation with id " + id + " not found in DB.");
        }
        log.info("Geographic Address Validation " + id + " retrieved.");
        return retrieved.get();
    }

    @Transactional
    public GeographicAddressValidation patch(String id, GeographicAddressValidationUpdate geographicAddressValidationUpdate, String lastUpdate) throws NotExistingEntityException {
        log.info("Received request to patch Product Offering with id " + id + ".");
        Optional<GeographicAddressValidation> toUpdate = geographicAddressValidationRepository.findByGeographicAddressValidationId(id);
        if (!toUpdate.isPresent()) {
            throw new NotExistingEntityException("Product Offering with id " + id + " not found in DB.");
        }
        GeographicAddressValidation geographicAddressValidation = updateGeographicAddressValidation(toUpdate.get(), geographicAddressValidationUpdate);
        geographicAddressValidationRepository.save(geographicAddressValidation);
        log.info("Geographic Address Validation " + id + " patched.");
        return geographicAddressValidation;
    }

    @Transactional
    public void delete(String id) {
        geographicAddressValidationRepository.deleteById(id);
    }

    private GeographicAddressValidation createAndPopulateGeographicAddressValidation(GeographicAddressValidationCreate geographicAddressValidationCreate) {
        final String id = UUID.randomUUID().toString();
        return new GeographicAddressValidation()
                .schemaLocation(geographicAddressValidationCreate.getSchemaLocation())
                .type(geographicAddressValidationCreate.getType())
                .href(protocol + hostname + ":" + port + path + id)
                .id(id)
                .validGeographicAddress(geographicAddressValidationCreate.getSubmittedGeographicAddress());
    }

    private GeographicAddressValidation updateGeographicAddressValidation(GeographicAddressValidation geographicAddressValidation, GeographicAddressValidationUpdate geographicAddressValidationUpdate) {
        return geographicAddressValidation
                .validationDate(geographicAddressValidationUpdate.getValidationDate())
                .validationResult(geographicAddressValidationUpdate.getValidationResult())
                .status(geographicAddressValidationUpdate.getStatus())
                .validGeographicAddress(geographicAddressValidationUpdate.getValidGeographicAddress())
                .schemaLocation(geographicAddressValidationUpdate.getSchemaLocation())
                .type(geographicAddressValidationUpdate.getType());
    }

}
