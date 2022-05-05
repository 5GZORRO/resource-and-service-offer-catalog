package it.nextworks.tmf_offering_catalog.services;

import it.nextworks.tmf_offering_catalog.common.exception.DIDGenerationRequestException;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.common.exception.ProductOrderDeleteScLCMException;
import it.nextworks.tmf_offering_catalog.common.exception.StakeholderNotRegisteredException;
import it.nextworks.tmf_offering_catalog.information_models.common.PlaceRef;
import it.nextworks.tmf_offering_catalog.information_models.common.RelatedParty;
import it.nextworks.tmf_offering_catalog.information_models.party.OrganizationWrapper;
import it.nextworks.tmf_offering_catalog.information_models.product.*;
import it.nextworks.tmf_offering_catalog.information_models.product.order.*;
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

    @Transactional
    public ProductOrder create(ProductOrderCreate productOrderCreate) throws IOException, StakeholderNotRegisteredException, DIDGenerationRequestException {
        log.info("Received request to create a Product Order.");
        ProductOrder productOrder = productOrderRepository.save(new ProductOrder(productOrderCreate));
        productOrder.href(protocol + hostname + ":" + port + path + productOrder.getId());

        OrganizationWrapper ow;
        try {
            ow = organizationService.get();
        } catch (NotExistingEntityException e) {
            throw new StakeholderNotRegisteredException(e.getMessage());
        }

        try {
            productOrderCommunicationService.requestDID(productOrder.getId(), ow.getToken());
        } catch (DIDGenerationRequestException e) {
            productOrderRepository.delete(productOrder);
            throw e;
        }

        log.info("Product Order created with id " + productOrder.getId() + ".");
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

    public ProductOrder patch(String id, ProductOrderUpdate productOrderUpdate)
            throws NotExistingEntityException {

        log.info("Received request to patch Product Order with id " + id + ".");

        Optional<ProductOrder> toUpdate = productOrderRepository.findByProductOrderId(id);
        if(!toUpdate.isPresent())
            throw new NotExistingEntityException("Product Order with id " + id + " not found in DB.");

        ProductOrder productOrder = toUpdate.get();

        productOrder.setBaseType(productOrderUpdate.getBaseType());
        productOrder.setSchemaLocation(productOrder.getSchemaLocation());
        productOrder.setType(productOrderUpdate.getType());

        final List<AgreementRef> agreement = productOrderUpdate.getAgreement();
        if(productOrder.getAgreement() == null)
            productOrder.setAgreement(agreement);
        else if(agreement != null) {
            productOrder.getAgreement().clear();
            productOrder.getAgreement().addAll(agreement);
        }
        else
            productOrder.getAgreement().clear();

        productOrder.setBillingAccount(productOrderUpdate.getBillingAccount());
        productOrder.setCancellationDate(productOrderUpdate.getCancellationDate());
        productOrder.setCancellationReason(productOrderUpdate.getCancellationReason());
        productOrder.setCategory(productOrderUpdate.getCategory());

        final List<RelatedChannel> channel = productOrderUpdate.getChannel();
        if(productOrder.getChannel() == null)
            productOrder.setChannel(channel);
        else if(channel != null) {
            productOrder.getChannel().clear();
            productOrder.getChannel().addAll(channel);
        }
        else
            productOrder.getChannel().clear();

        productOrder.setDescription(productOrderUpdate.getDescription());
        productOrder.setExternalId(productOrderUpdate.getExternalId());

        final List<Note> note = productOrderUpdate.getNote();
        if(productOrder.getNote() == null)
            productOrder.setNote(note);
        else if(note != null) {
            productOrder.getNote().clear();
            productOrder.getNote().addAll(note);
        }
        else
            productOrder.getNote().clear();

        productOrder.setNotificationContact(productOrderUpdate.getNotificationContact());

        final List<OrderPrice> orderTotalPrice = productOrderUpdate.getOrderTotalPrice();
        if(productOrder.getOrderTotalPrice() == null)
            productOrder.setOrderTotalPrice(orderTotalPrice);
        else if(orderTotalPrice != null) {
            productOrder.getOrderTotalPrice().clear();
            productOrder.getOrderTotalPrice().addAll(orderTotalPrice);
        }
        else
            productOrder.getOrderTotalPrice().clear();

        final List<PaymentRef> payment = productOrderUpdate.getPayment();
        if(productOrder.getPayment() == null)
            productOrder.setPayment(payment);
        else if(payment != null) {
            productOrder.getPayment().clear();
            productOrder.getPayment().addAll(payment);
        }
        else
            productOrder.getPayment().clear();

        productOrder.setPriority(productOrderUpdate.getPriority());

        final List<ProductOfferingQualificationRef> productOfferingQualification = productOrderUpdate.getProductOfferingQualification();
        if(productOrder.getProductOfferingQualification() == null)
            productOrder.setProductOfferingQualification(productOfferingQualification);
        else if(productOfferingQualification != null) {
            productOrder.getProductOfferingQualification().clear();
            productOrder.getProductOfferingQualification().addAll(productOfferingQualification);
        }
        else
            productOrder.getProductOfferingQualification().clear();

        final List<ProductOrderItem> productOrderItem = productOrderUpdate.getProductOrderItem();
        if(productOrder.getProductOrderItem() == null)
            productOrder.setProductOrderItem(productOrderItem);
        else if(productOrderItem != null) {
            productOrder.getProductOrderItem().clear();
            productOrder.getProductOrderItem().addAll(productOrderItem);
        }
        else
            productOrder.getProductOrderItem().clear();

        final List<QuoteRef> quote = productOrderUpdate.getQuote();
        if(productOrder.getQuote() == null)
            productOrder.setQuote(quote);
        else if(quote != null) {
            productOrder.getQuote().clear();
            productOrder.getQuote().addAll(quote);
        }
        else
            productOrder.getQuote().clear();

        final List<RelatedParty> relatedParty = productOrderUpdate.getRelatedParty();
        if(productOrder.getRelatedParty() == null)
            productOrder.setRelatedParty(relatedParty);
        else if(relatedParty != null) {
            productOrder.getRelatedParty().clear();
            productOrder.getRelatedParty().addAll(relatedParty);
        }
        else
            productOrder.getRelatedParty().clear();

        productOrder.setRequestedCompletionDate(productOrderUpdate.getRequestedCompletionDate());
        productOrder.setRequestedStartDate(productOrderUpdate.getRequestedStartDate());

        productOrderRepository.save(productOrder);

        log.info("Product Order " + id + " patched.");

        return productOrder;
    }

}
