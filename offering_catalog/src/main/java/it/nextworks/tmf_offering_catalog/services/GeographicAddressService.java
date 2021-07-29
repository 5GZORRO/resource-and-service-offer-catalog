package it.nextworks.tmf_offering_catalog.services;

import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddress;
import it.nextworks.tmf_offering_catalog.repo.GeographicAddressRepository;
import it.nextworks.tmf_offering_catalog.rest.filter.GeographicAddressFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GeographicAddressService {

    private static final Logger log = LoggerFactory.getLogger(GeographicAddressService.class);

    @Autowired
    private GeographicAddressRepository geographicAddressRepository;

    @Transactional
    public List<GeographicAddress> list(GeographicAddressFilter geographicAddressFilter) {
        log.info("Received request to retrieve all Geographic Addresses");
        if (geographicAddressFilter != null) {
            return geographicAddressRepository.filteredFindAll(geographicAddressFilter);
        } else {
            return geographicAddressRepository.findAll();
        }
    }

    @Transactional
    public GeographicAddress get(String id) throws NotExistingEntityException {
        log.info("Received request to retrieve Geographic Address with id " + id + ".");
        Optional<GeographicAddress> retrieved = geographicAddressRepository.findByGeographicAddressId(id);
        if (!retrieved.isPresent()) {
            throw new NotExistingEntityException("Geographic Address with id " + id + " not found in DB.");
        }
        return retrieved.get();
    }

}
