package it.nextworks.tmf_offering_catalog.scheduled;

import it.nextworks.tmf_offering_catalog.information_models.product.ProductOffering;
import it.nextworks.tmf_offering_catalog.services.ProductOfferingService;
import it.nextworks.tmf_offering_catalog.services.ProductOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.threeten.bp.OffsetDateTime;

import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class ObsoleteOffersTask {
    private static final Logger log = LoggerFactory.getLogger(ProductOrderService.class);

    @Autowired
    ProductOfferingService productOfferingService;

    //Scheduled to start every day at 01:00 a.m.
    @Scheduled(cron = "${cron.schedule}")
    public void execute() {

        List<ProductOffering> lpos = productOfferingService.list();

        for (ProductOffering elem: lpos) {
            log.info("po id " + elem.getId());

            Date offerStartDate = new Date(OffsetDateTime.parse(elem.getValidFor().getStartDateTime()).toInstant().toEpochMilli());
            Date offerEndDate = new Date(OffsetDateTime.parse(elem.getValidFor().getEndDateTime()).toInstant().toEpochMilli());
            Date currentDate = new Date(System.currentTimeMillis());

            //If the enddate of the offer is less than the current date the period is out to be valid and life cycle status is updated to OBSOLETE
            if (offerEndDate.before(currentDate)) {
                try {
                    productOfferingService.obsoleteProductOfferingLifeCycleStatus(elem);
                } catch (Exception e) {
                    log.info("Unable to update Product Offering Status for id " + elem.getId());
                }
            }
        }

    }
}
