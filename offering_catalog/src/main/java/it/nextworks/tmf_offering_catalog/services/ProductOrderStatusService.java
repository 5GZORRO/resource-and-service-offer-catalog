package it.nextworks.tmf_offering_catalog.services;

import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderStatesEnum;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderStatus;
import it.nextworks.tmf_offering_catalog.repo.ProductOrderStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductOrderStatusService {

    private static final Logger log = LoggerFactory.getLogger(ProductOrderStatusService.class);

    private ProductOrderStatusRepository productOrderStatusRepository;

    @Autowired
    public ProductOrderStatusService(ProductOrderStatusRepository productOrderStatusRepository) {
        this.productOrderStatusRepository = productOrderStatusRepository;
    }

    public ProductOrderStatus get(String id) throws NotExistingEntityException {

        log.info("Received request to retrieve Product Order Status with id " + id + ".");

        Optional<ProductOrderStatus> retrieved = productOrderStatusRepository.findById(id);
        if(!retrieved.isPresent())
            throw new NotExistingEntityException("Product Order Status with id " + id + " not found in DB.");

        log.info("Product Order Status " + id + " retrieved.");

        return retrieved.get();
    }

    public void updateProductOrderStatus(String id, String instanceId)
            throws NotExistingEntityException {

        log.info("Received request to update Product Order Status with id " + id + ".");

        Optional<ProductOrderStatus> retrieved = productOrderStatusRepository.findById(id);
        if(!retrieved.isPresent())
            throw new NotExistingEntityException("Product Order Status with id " + id + " not found in DB.");

        ProductOrderStatus productOrderStatus = retrieved.get();
        productOrderStatus.setStatus(ProductOrderStatesEnum.INSTANTIATED);
        productOrderStatus.setInstanceId(instanceId);
        productOrderStatusRepository.save(productOrderStatus);

        log.info("Product Order Status with id " + id + " updated.");
    }

}
