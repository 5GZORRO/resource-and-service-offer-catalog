package it.nextworks.tmf_offering_catalog.services;

import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.product.*;
import it.nextworks.tmf_offering_catalog.repo.GeographicAddressValidationRepository;
import it.nextworks.tmf_offering_catalog.repo.GeographicLocationRepository;
import it.nextworks.tmf_offering_catalog.repo.GeographicPointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private static final String gaPath = "/tmf-api/geographicAddressManagement/v4/geographicAddress/";

    @Autowired
    private GeographicAddressValidationRepository geographicAddressValidationRepository;

    @Autowired
    private GeographicLocationRepository geographicLocationRepository;

    @Autowired
    private GeographicPointRepository geographicPointRepository;

    @Transactional
    public GeographicAddressValidation create(GeographicAddressValidationCreate geographicAddressValidationCreate) {
        log.info("Received request to create a Geographic Address.");
        GeographicAddressValidation geographicAddressValidation = geographicAddressValidationRepository
                .save(createAndPopulateGeographicAddressValidation(geographicAddressValidationCreate));
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
            throw new NotExistingEntityException("Geographic Address Validation with id " + id + " not found in DB.");
        }
        GeographicAddressValidation geographicAddressValidation = updateGeographicAddressValidation(toUpdate.get(), geographicAddressValidationUpdate);
        geographicAddressValidationRepository.save(geographicAddressValidation);
        log.info("Geographic Address Validation " + id + " patched.");
        return geographicAddressValidation;
    }

    @Transactional
    public void delete(String id) {
        Optional<GeographicAddressValidation> toDelete = geographicAddressValidationRepository.findByGeographicAddressValidationId(id);
        if(toDelete.isPresent())
            geographicAddressValidationRepository.delete(toDelete.get());
    }

    private GeographicAddressValidation createAndPopulateGeographicAddressValidation(GeographicAddressValidationCreate geographicAddressValidationCreate) {

        GeographicAddressCreate submittedGeographicAddress = geographicAddressValidationCreate.getSubmittedGeographicAddress();
        GeographicLocation geographicLocation = submittedGeographicAddress.getGeographicLocation();

        if(geographicLocation != null) {
            if(geographicLocation.getId() != null) {
                Optional<GeographicLocation> opt =
                        geographicLocationRepository.findByGeographicLocationId(geographicLocation.getId());
                opt.ifPresent(submittedGeographicAddress::setGeographicLocation);
            } else {
                geographicLocation.setId(UUID.randomUUID().toString());

                List<GeographicPoint> geographicPoints = geographicLocation.getGeometry();
                List<GeographicPoint> newGeographicPoints = new ArrayList<>();
                if(geographicPoints != null) {
                    for(GeographicPoint geographicPoint : geographicPoints) {
                       String id = geographicPoint.getId();
                       if(id != null) {
                           Optional<GeographicPoint> opt = geographicPointRepository.findByGeographicPointId(id);
                           if(!opt.isPresent())
                               continue;
                           newGeographicPoints.add(opt.get());
                       } else {
                           geographicPoint.setId(UUID.randomUUID().toString());
                           newGeographicPoints.add(geographicPoint);
                       }
                    }

                    geographicLocation.setGeometry(newGeographicPoints);
                }
            }
        }

        String id = UUID.randomUUID().toString();
        String geographicAddressId = UUID.randomUUID().toString();
        return new GeographicAddressValidation()
                .id(id)
                .href(protocol + hostname + ":" + port + path + id)
                .schemaLocation(geographicAddressValidationCreate.getSchemaLocation())
                .type(geographicAddressValidationCreate.getType())
                .validGeographicAddress(new GeographicAddress()
                        .id(geographicAddressId)
                        .href(protocol + hostname + ":" + port + gaPath + geographicAddressId)
                        .geographicLocation(submittedGeographicAddress.getGeographicLocation())
                        .city(submittedGeographicAddress.getCity())
                        .country(submittedGeographicAddress.getCountry())
                        .locality(submittedGeographicAddress.getLocality())
                        .postcode(submittedGeographicAddress.getPostcode())
                        .stateOrProvince(submittedGeographicAddress.getStateOrProvince())
                        .streetName(submittedGeographicAddress.getStreetName())
                        .streetNr(submittedGeographicAddress.getStreetNr())
                        .streetNrLast(submittedGeographicAddress.getStreetNrLast())
                        .streetNrLastSuffix(submittedGeographicAddress.getStreetNrLastSuffix())
                        .streetNrSuffix(submittedGeographicAddress.getStreetNrSuffix())
                        .streetSuffix(submittedGeographicAddress.getStreetSuffix())
                        .streetType(submittedGeographicAddress.getStreetType())
                        .type(submittedGeographicAddress.getType()));
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
