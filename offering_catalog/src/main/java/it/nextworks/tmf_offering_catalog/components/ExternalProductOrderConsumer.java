package it.nextworks.tmf_offering_catalog.components;

import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.kafka.ExternalProductOrder;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrder;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderStatesEnum;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderStatus;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderUpdate;
import it.nextworks.tmf_offering_catalog.repo.ProductOrderRepository;
import it.nextworks.tmf_offering_catalog.repo.ProductOrderStatusRepository;
import it.nextworks.tmf_offering_catalog.services.ProductOfferingPriceService;
import it.nextworks.tmf_offering_catalog.services.ProductOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ExternalProductOrderConsumer {

    private static final Logger log = LoggerFactory.getLogger(ExternalProductOrderConsumer.class);

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired
    private ProductOrderStatusRepository productOrderStatusRepository;

    @Autowired
    private ProductOrderService productOrderService;

    @KafkaListener(
            topics = "#{@topic}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listener(ExternalProductOrder externalProductOrder) {

        log.info("Receiving External Product Order.");

        if (externalProductOrder == null) {
            log.warn("Received empty External Product Order.");
            return;
        }

        ProductOrder productOrder = externalProductOrder.getProductOrder();
        if (productOrder == null) {
            log.warn("Received empty Product Order in External Product Order.");
            return;
        }

        String did = externalProductOrder.getDid();
        if (did == null) {
            log.warn("Received empty DID in External Product Order.");
            return;
        }

        syncProductOrder(productOrder, did);
    }

    private void syncProductOrder(ProductOrder productOrder, String did) {
        String id = productOrder.getId();

        log.info("Syncing Product Order " + id + ".");

        Optional<ProductOrder> optionalProductOrder = productOrderRepository.findByProductOrderId(id);

        if (!optionalProductOrder.isPresent()) {
            productOrderRepository.save(productOrder);
            ProductOrderStatus productOrderStatus = new ProductOrderStatus()
                    .catalogId(id)
                    .did(did)
                    .status(ProductOrderStatesEnum.EXTERNAL);
            productOrderStatusRepository.save(productOrderStatus);
            log.info("Synced Product Order " + id + " (new).");
            return;
        }

        Optional<ProductOrderStatus> optionalProductOrderStatus = productOrderStatusRepository.findById(id);
        if (!optionalProductOrderStatus.isPresent()) {
            log.error("Product Order Status " + id + " not found in DB.");
            return;
        }
        if (optionalProductOrderStatus.get().getStatus() != ProductOrderStatesEnum.EXTERNAL) {
            log.info("Product Order " + id + " not external, skip.");
            return;
        }

        ProductOrderUpdate productOrderUpdate = new ProductOrderUpdate()
                .baseType(productOrder.getBaseType())
                .schemaLocation(productOrder.getSchemaLocation())
                .type(productOrder.getType())
                .agreement(productOrder.getAgreement())
                .billingAccount(productOrder.getBillingAccount())
                .cancellationDate(productOrder.getCancellationDate())
                .cancellationReason(productOrder.getCancellationReason())
                .category(productOrder.getCategory())
                .channel(productOrder.getChannel())
                .description(productOrder.getDescription())
                .externalId(productOrder.getExternalId())
                .note(productOrder.getNote())
                .notificationContact(productOrder.getNotificationContact())
                .orderTotalPrice(productOrder.getOrderTotalPrice())
                .payment(productOrder.getPayment())
                .priority(productOrder.getPriority())
                .productOfferingQualification(productOrder.getProductOfferingQualification())
                .productOrderItem(productOrder.getProductOrderItem())
                .quote(productOrder.getQuote())
                .relatedParty(productOrder.getRelatedParty())
                .requestedCompletionDate(productOrder.getRequestedCompletionDate())
                .requestedStartDate(productOrder.getRequestedStartDate());

        try {
            productOrderService.patch(id, productOrderUpdate);
        } catch (NotExistingEntityException e) {
            log.error("External " + e.getMessage());
        }

        log.info("Synced Product Order " + id + " (update).");

    }

}
