package it.nextworks.tmf_offering_catalog.services;

import it.nextworks.tmf_offering_catalog.common.exception.*;
import it.nextworks.tmf_offering_catalog.information_models.common.Any;
import it.nextworks.tmf_offering_catalog.information_models.common.ServiceSpecificationRef;
import it.nextworks.tmf_offering_catalog.information_models.party.OrganizationWrapper;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOffering;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingRef;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductSpecification;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrder;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderCreate;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderItem;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceSpecCharacteristic;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceSpecCharacteristicValue;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceSpecification;
import it.nextworks.tmf_offering_catalog.repo.ProductOrderRepository;
import it.nextworks.tmf_offering_catalog.rest.ProductOrderController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private ProductOfferingService productOfferingService;

    @Autowired
    private ProductSpecificationService productSpecificationService;

    @Autowired
    private ServiceSpecificationService serviceSpecificationService;

    @Autowired
    private ProductOrderCommunicationService productOrderCommunicationService;

    @Autowired
    private NSSOCommunicationService nssoCommunicationService;

    public ProductOrder create(ProductOrderController.ProductOrderWInstantiationRequest productOrderWInstantiationRequest,
                               Boolean skipIDP) throws IOException, StakeholderNotRegisteredException,
            DIDGenerationRequestException, NotExistingEntityException, MalformedProductOrderException,
            NSSORequestException, MalformedProductOfferingException {
        log.info("Received request to create a Product Order.");

        OrganizationWrapper ow;
        try {
            ow = organizationService.get();
        } catch (NotExistingEntityException e) {
            throw new StakeholderNotRegisteredException(e.getMessage());
        }

        ProductOrderCreate productOrderCreate = productOrderWInstantiationRequest.getProductOrderCreate();
        ProductOrderController.Auth auth = productOrderWInstantiationRequest.getAuth();
        Map<String, String> sliceManagerParams = productOrderWInstantiationRequest.getSliceManagerParams();

        List<ProductOrderItem> productOrderItems = productOrderCreate.getProductOrderItem();
        if(productOrderItems == null)
            throw new MalformedProductOrderException("NULL ProductOrderItem List, abort.");
        if(productOrderItems.isEmpty())
            throw new MalformedProductOrderException("Empty ProductOrderItem List, abort.");
        for(ProductOrderItem productOrderItem : productOrderItems) {
            ProductOfferingRef poRef = productOrderItem.getProductOffering();
            if(poRef == null)
                throw new MalformedProductOrderException("NULL Product Offering reference, abort.");

                productOfferingService.get(poRef.getId());
        }

        ProductOrder productOrder = productOrderRepository.save(new ProductOrder(productOrderCreate));
        String productOrderId = productOrder.getId();
        productOrder.href(protocol + hostname + ":" + port + path + productOrderId);
        productOrder = productOrderRepository.save(productOrder);

        log.info("Product Order " + productOrderId + " stored in Catalog.");

        if(skipIDP != null) {
            if(!skipIDP) {
                try {
                    productOrderCommunicationService.requestDID(productOrderId, ow.getToken());
                } catch (DIDGenerationRequestException e) {
                    productOrderRepository.delete(productOrder);
                    throw e;
                }
            }
        } else {
            try {
                productOrderCommunicationService.requestDID(productOrderId, ow.getToken());
            } catch (DIDGenerationRequestException e) {
                productOrderRepository.delete(productOrder);
                throw e;
            }
        }

        log.info("Product Order created with id " + productOrderId + ".");

        // Consider only one product offering
        String productOfferingId = productOrder.getProductOrderItem().get(0).getProductOffering().getId();
        ProductOffering productOffering = productOfferingService.get(productOfferingId);
        ProductSpecification productSpecification =
                productSpecificationService.get(productOffering.getProductSpecification().getId());
        // Consider one service specification
        List<ServiceSpecificationRef> serviceSpecificationRefs = productSpecification.getServiceSpecification();
        ServiceSpecification ss = serviceSpecificationService.get(serviceSpecificationRefs.get(0).getId());
        List<ServiceSpecCharacteristic> serviceSpecCharacteristics =
                ss.getServiceSpecCharacteristic().stream().filter(serviceSpecCharacteristic ->
                        serviceSpecCharacteristic.getName().equals("vsdId")).collect(Collectors.toList());
        if(serviceSpecCharacteristics.isEmpty())
            throw new MalformedProductOfferingException("Missing vsdId, abort.");
        if(serviceSpecCharacteristics.size() > 1)
            throw new MalformedProductOfferingException("Multiple vsdId, abort.");
        ServiceSpecCharacteristic vsdIdCharacteristic = serviceSpecCharacteristics.get(0);
        List<ServiceSpecCharacteristicValue> vsdIdCharacteristicValues =
                vsdIdCharacteristic.getServiceSpecCharacteristicValue();
        if(vsdIdCharacteristicValues.isEmpty())
            throw new MalformedProductOfferingException("Missing vsdId, abort.");
        if(vsdIdCharacteristicValues.size() > 1)
            throw new MalformedProductOfferingException("Multiple vsdId, abort.");
        Any vsdIdValue = vsdIdCharacteristicValues.get(0).getValue();
        if(vsdIdValue == null)
            throw new MalformedProductOfferingException("Missing vsdId, abort.");
        String vsdId = vsdIdValue.getValue();
        if(vsdId == null)
            throw new MalformedProductOfferingException("Missing vsdId, abort.");

        String jSessionId = nssoCommunicationService.login(auth.getUsr(), auth.getPsw());

        nssoCommunicationService.instantiateVS(vsdId, auth.getTenantId(), productOrderId,
                productOfferingId, sliceManagerParams, jSessionId);

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
        productOrderCommunicationService.deleteProductOrder(id);
        productOrderRepository.deleteById(id);
    }

}
