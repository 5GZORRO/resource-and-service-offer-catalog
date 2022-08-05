package it.nextworks.tmf_offering_catalog.scheduled;

import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOffering;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingStatesEnum;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingStatus;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderStatesEnum;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderStatus;
import it.nextworks.tmf_offering_catalog.repo.ProductOfferingStatusRepository;
import it.nextworks.tmf_offering_catalog.repo.ProductOrderStatusRepository;
import it.nextworks.tmf_offering_catalog.services.ProductOfferingService;
import it.nextworks.tmf_offering_catalog.services.ProductOfferingStatusService;
import it.nextworks.tmf_offering_catalog.services.ProductOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.threeten.bp.OffsetDateTime;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Configuration
@EnableScheduling
public class ObsoleteOffersTask {
    private static final Logger log = LoggerFactory.getLogger(ProductOrderService.class);

    @Autowired
    ProductOfferingService pos;

    @Autowired
    private ProductOfferingStatusRepository productOfferingStatusRepository;

    @Autowired
    ProductOfferingStatusService productOfferingStatusService;

    //Scheduled to start every day at 01:00 a.m.
    @Scheduled(cron = "${cron.schedule}")
    public void execute() {

        List<ProductOffering> lpos = pos.list();

        for (ProductOffering elem: lpos) {
            log.info("po id " + elem.getId());

            Date offerStartDate = new Date(OffsetDateTime.parse(elem.getValidFor().getStartDateTime()).toInstant().toEpochMilli());
            Date offerEndDate = new Date(OffsetDateTime.parse(elem.getValidFor().getEndDateTime()).toInstant().toEpochMilli());
            Date currentDate = new Date(System.currentTimeMillis());

            //If the enddate of the offer is less than the current date the period is out to be valid and it status is updated to OBSOLETE
            if (offerEndDate.before(currentDate)) {
                Optional<ProductOfferingStatus> toEnd = productOfferingStatusRepository.findById(elem.getId());
                if (!toEnd.isPresent()) {
                    try {
                        throw new NotExistingEntityException("Product Offering Status for id " + elem.getId() + " not found in DB.");
                    } catch (NotExistingEntityException e) {
                        log.info("Product Offering Status for id " + elem.getId() + " not found in DB.");
                    }
                } else {
                    ProductOfferingStatus productOfferingStatus = toEnd.get();
                    ProductOfferingStatesEnum productOfferingStatesEnum = productOfferingStatus.getStatus();
                    try {
                        productOfferingStatusService.obsoleteProductOfferingStatus(productOfferingStatus);
                    } catch (Exception e) {
                        log.info("Unable to update Product Offering Status for id " + elem.getId());
                    }
                }
            }
        }

    }
}
