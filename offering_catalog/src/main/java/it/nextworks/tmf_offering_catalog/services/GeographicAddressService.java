package it.nextworks.tmf_offering_catalog.services;

import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddress;
import it.nextworks.tmf_offering_catalog.repo.GeographicAddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GeographicAddressService {

    private static final Logger log = LoggerFactory.getLogger(GeographicAddressService.class);

    @Autowired
    private GeographicAddressRepository geographicAddressRepository;

    @Transactional
    public List<GeographicAddress> list() {
        log.info("Received request to retrieve all Geographic Addresses");
        return geographicAddressRepository.findAll();
    }
}
