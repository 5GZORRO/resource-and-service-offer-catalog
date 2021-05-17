package it.nextworks.tmf_offering_catalog.services;

import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicSubAddress;
import it.nextworks.tmf_offering_catalog.repo.GeographicSubAddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GeographicSubAddressService {

    private static final Logger log = LoggerFactory.getLogger(GeographicSubAddressService.class);

    @Autowired
    private GeographicSubAddressRepository geographicSubAddressRepository;

    @Transactional
    public List<GeographicSubAddress> list() {
        log.info("Received request to retrieve all Geographic Sub Addresses");
        return geographicSubAddressRepository.findAll();
    }

    @Transactional
    public GeographicSubAddress get(String id) throws NotExistingEntityException {
        log.info("Received request to retrieve Geographic Sub Address with id " + id + ".");
        Optional<GeographicSubAddress> retrieved = geographicSubAddressRepository.findByGeographicSubAddressId(id);
        if (!retrieved.isPresent()) {
            throw new NotExistingEntityException("Geographic Sub Address with id " + id + " not found in DB.");
        }
        return retrieved.get();
    }

}
