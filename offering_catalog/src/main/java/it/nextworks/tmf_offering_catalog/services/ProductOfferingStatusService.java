package it.nextworks.tmf_offering_catalog.services;

import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingStatesEnum;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingStatus;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderStatesEnum;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderStatus;
import it.nextworks.tmf_offering_catalog.repo.ProductOfferingStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductOfferingStatusService {

    private static final Logger log = LoggerFactory.getLogger(ProductOfferingStatusService.class);

    @Autowired
    private ProductOfferingStatusRepository productOfferingStatusRepository;

    public ProductOfferingStatus get(String id) throws NotExistingEntityException {

        log.info("Received request to retrieve Product Offering Status with id " + id + ".");

        Optional<ProductOfferingStatus> retrieved = productOfferingStatusRepository.findById(id);
        if(!retrieved.isPresent())
            throw new NotExistingEntityException("Product Offering Status with id " + id + " not found in DB.");

        log.info("Product Offering Status " + id + " retrieved.");

        return retrieved.get();
    }

    public List<ProductOfferingStatus> list() {

        log.info("Received request to retrieve all Product Offering States.");

        List<ProductOfferingStatus> productOfferingStatuses = productOfferingStatusRepository.findAll();

        log.info("Product Offering States retrieved.");

        return productOfferingStatuses;
    }

}
