package it.nextworks.tmf_offering_catalog.services;

import it.nextworks.tmf_offering_catalog.common.exception.DIDGenerationRequestException;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.common.exception.ProductOrderDeleteScLCMException;
import it.nextworks.tmf_offering_catalog.common.exception.StakeholderNotRegisteredException;
import it.nextworks.tmf_offering_catalog.information_models.common.RelatedParty;
import it.nextworks.tmf_offering_catalog.information_models.party.OrganizationWrapper;
import it.nextworks.tmf_offering_catalog.information_models.product.*;
import it.nextworks.tmf_offering_catalog.information_models.product.order.*;
import it.nextworks.tmf_offering_catalog.repo.ProductOrderRepository;
import it.nextworks.tmf_offering_catalog.repo.ProductOrderStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.threeten.bp.OffsetDateTime;

import java.io.IOException;
import java.util.Date;
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

    @Value("${ingress:}")
    private String ingres;

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired
    private ProductOrderStatusRepository productOrderStatusRepository;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private ProductOrderCommunicationService productOrderCommunicationService;

    @Autowired
    private ProductOfferingService productOfferingService;

    public ProductOrder create(ProductOrderCreate productOrderCreate) throws IOException, StakeholderNotRegisteredException, DIDGenerationRequestException {
        log.info("Received request to create a Product Order.");

        OrganizationWrapper ow;
        try {
            ow = organizationService.get();
        } catch (NotExistingEntityException e) {
            throw new StakeholderNotRegisteredException(e.getMessage());
        }

        //check if the offer validity period >= price validity period
        try {
            if (productOrderCreate.getProductOrderItem().size() > 0 && productOrderCreate.getProductOrderItem().get(0).getProductOffering().getId() != null) {
                ProductOffering productOffering = productOfferingService.get(productOrderCreate.getProductOrderItem().get(0).getProductOffering().getId());
                if (productOffering == null) {
                    log.info("Offering Id " + productOrderCreate.getProductOrderItem().get(0).getProductOffering().getId() + " do not exist to create Order");
                    throw new NotExistingEntityException("Offering Id " + productOrderCreate.getProductOrderItem().get(0).getProductOffering().getId() + " do not exist to create Order");
                }

                Date offerStartDate = new Date(OffsetDateTime.parse(productOffering.getValidFor().getStartDateTime()).toInstant().toEpochMilli());
                Date offerEndDate = new Date(OffsetDateTime.parse(productOffering.getValidFor().getEndDateTime()).toInstant().toEpochMilli());
                Date requestedStartDate = new Date(OffsetDateTime.parse(productOrderCreate.getRequestedStartDate()).toInstant().toEpochMilli());
                Date requestedCompletionDate = new Date(OffsetDateTime.parse(productOrderCreate.getRequestedCompletionDate()).toInstant().toEpochMilli());
                if ((requestedStartDate.equals(offerStartDate) || requestedStartDate.after(offerStartDate)) &&
                    (requestedCompletionDate.equals(offerEndDate) || requestedCompletionDate.before(offerEndDate))) {

                } else {
                    log.info("Invalid order period for offer to create product order");
                    throw new StakeholderNotRegisteredException("Invalid order period for offer to create product order");
                }
            }
        } catch (Exception e) {
            throw new StakeholderNotRegisteredException(e.getMessage());
        }

        ProductOrder productOrder = productOrderRepository.save(new ProductOrder(productOrderCreate));
        String productOrderId = productOrder.getId();
        productOrder.href(StringUtils.hasText(ingres) ? ingres : (protocol + hostname + ":" + port) + path + productOrderId);
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
    public void end(String id) throws NotExistingEntityException, ProductOrderDeleteScLCMException, IOException {
        productOrderCommunicationService.endProductOrder(id);
    }

    public ProductOrder patch(String did, ProductOrderUpdate productOrderUpdate, boolean deleted) throws NotExistingEntityException {

        log.info("Received request to patch Product Order with did " + did + ".");

        Optional<ProductOrderStatus> optionalProductOrderStatus = productOrderStatusRepository.findByProductOrderStatusDid(did);

        if (!optionalProductOrderStatus.isPresent()) {
            throw new NotExistingEntityException("Product Order Status with did " + did + " not found in DB.");
        }

        Optional<ProductOrder> toUpdate = productOrderRepository.findByProductOrderId(optionalProductOrderStatus.get().getCatalogId());
        if (!toUpdate.isPresent()) {
            throw new NotExistingEntityException("Product Order with id " + optionalProductOrderStatus.get().getCatalogId() + " not found in DB.");
        }

        ProductOrder productOrder = toUpdate.get();

        productOrder.setBaseType(productOrderUpdate.getBaseType());
        productOrder.setSchemaLocation(productOrder.getSchemaLocation());
        productOrder.setType(productOrderUpdate.getType());

        if (productOrder.getAgreement() == null) {
            productOrder.setAgreement(productOrderUpdate.getAgreement());
        }

        productOrder.setBillingAccount(productOrderUpdate.getBillingAccount());
        productOrder.setCancellationDate(productOrderUpdate.getCancellationDate());
        productOrder.setCancellationReason(productOrderUpdate.getCancellationReason());
        productOrder.setCategory(productOrderUpdate.getCategory());

        if (productOrder.getChannel() == null) {
            productOrder.setChannel(productOrderUpdate.getChannel());
        }

        productOrder.setDescription(productOrderUpdate.getDescription());
        productOrder.setExternalId(productOrderUpdate.getExternalId());

        if (productOrder.getNote() == null) {
            productOrder.setNote(productOrderUpdate.getNote());
        }

        productOrder.setNotificationContact(productOrderUpdate.getNotificationContact());

        if (productOrder.getOrderTotalPrice() == null) {
            productOrder.setOrderTotalPrice(productOrderUpdate.getOrderTotalPrice());
        }

        if (productOrder.getPayment() == null) {
            productOrder.setPayment(productOrderUpdate.getPayment());
        }

        productOrder.setPriority(productOrderUpdate.getPriority());

        if (productOrder.getProductOfferingQualification() == null) {
            productOrder.setProductOfferingQualification(productOrderUpdate.getProductOfferingQualification());
        }

        if (productOrder.getProductOrderItem() == null) {
            productOrder.setProductOrderItem(productOrderUpdate.getProductOrderItem());
        }

        if (productOrder.getQuote() == null) {
            productOrder.setQuote(productOrderUpdate.getQuote());
        }

        if (productOrder.getRelatedParty() == null) {
            productOrder.setRelatedParty(productOrderUpdate.getRelatedParty());
        }

        productOrder.setRequestedCompletionDate(productOrderUpdate.getRequestedCompletionDate());
        productOrder.setRequestedStartDate(productOrderUpdate.getRequestedStartDate());

        if (deleted) {
            productOrder.setState(ProductOrderStateType.CANCELLED);
            log.info("Product Order " + productOrder.getId() + " marked as cancelled.");
        }

        productOrderRepository.save(productOrder);

        log.info("Product Order " + productOrder.getId() + " patched.");

        return productOrder;
    }

    public void cancelProductOrderState(ProductOrder productOrder) {

        log.info("Received request to cancel Product Order State with catalog id " + productOrder.getId() + ".");

        productOrder.setState(ProductOrderStateType.CANCELLED);
        productOrderRepository.save(productOrder);

        log.info("Product Order State with catalog id " + productOrder.getId() + " to cancelled.");
    }
}
