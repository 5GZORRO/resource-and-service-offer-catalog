package it.nextworks.tmf_offering_catalog.services;

import it.nextworks.tmf_offering_catalog.common.exception.DIDGenerationRequestException;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.common.exception.ProductOrderDeleteScLCMException;
import it.nextworks.tmf_offering_catalog.common.exception.StakeholderNotRegisteredException;
import it.nextworks.tmf_offering_catalog.information_models.party.OrganizationWrapper;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrder;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderCreate;
import it.nextworks.tmf_offering_catalog.repo.ProductOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductOrderService {

    private static final Logger log = LoggerFactory.getLogger(ProductOrderService.class);

    private static final String protocol = "http://";
    @Value("${server.hostname}")
    private String hostname;
    @Value("${server.port}")
    private String port;
    private static final String path = "/tmf-api/productOrderingManagement/v4/productOrder/";

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private ProductOrderCommunicationService productOrderCommunicationService;

    public ProductOrder create(ProductOrderCreate productOrderCreate) throws IOException, StakeholderNotRegisteredException, DIDGenerationRequestException {
        log.info("Received request to create a Product Order.");

        OrganizationWrapper ow;
        try {
            ow = organizationService.get();
        } catch (NotExistingEntityException e) {
            throw new StakeholderNotRegisteredException(e.getMessage());
        }

        ProductOrder productOrder = productOrderRepository.save(new ProductOrder(productOrderCreate));
        String productOrderId = productOrder.getId();
        productOrder.href(protocol + hostname + ":" + port + path + productOrderId);
        productOrder = productOrderRepository.save(productOrder);

        log.info("Product Order " + productOrderId + " stored in Catalog.");

        try {
            productOrderCommunicationService.requestDID(productOrderId, ow.getToken());
        } catch (DIDGenerationRequestException e) {
            productOrderRepository.delete(productOrder);
            throw e;
        }

        log.info("Product Order created with id " + productOrderId + ".");
        return productOrder;
    }

    @Transactional
    public List<ProductOrder> list() {
        log.info("Received request to retrieve all Product Orders");
        return productOrderRepository.findAll();
    }

    @Transactional
    public ProductOrder get(String id) throws NotExistingEntityException {
        log.info("Received request to retrieve Product Order with id " + id + ".");
        Optional<ProductOrder> productOrderOptional = productOrderRepository.findById(id);
        if (!productOrderOptional.isPresent()) {
            throw new NotExistingEntityException("Product Order with id " + id + " not found in DB.");
        }
        log.info("Product Order " + id + " retrieved.");
        return productOrderOptional.get();
    }

    @Transactional
    public void delete(String id) throws NotExistingEntityException, ProductOrderDeleteScLCMException, IOException {
        // productOrderCommunicationService.deleteProductOrder(id);
        productOrderRepository.deleteById(id);
    }

}
