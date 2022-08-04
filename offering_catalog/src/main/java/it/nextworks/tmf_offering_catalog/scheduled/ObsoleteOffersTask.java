package it.nextworks.tmf_offering_catalog.scheduled;

import it.nextworks.tmf_offering_catalog.information_models.product.ProductOffering;
import it.nextworks.tmf_offering_catalog.services.ProductOfferingService;
import it.nextworks.tmf_offering_catalog.services.ProductOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Configuration
@EnableScheduling
public class ObsoleteOffersTask {
    private static final Logger log = LoggerFactory.getLogger(ProductOrderService.class);

    @Autowired
    ProductOfferingService pos;

    @Scheduled(cron = "${cron.schedule}")
    public void execute() {

        List<ProductOffering> lpos = pos.list();

        for (ProductOffering elem: lpos){
            log.info("po id " + elem.getId());
        }

    }
}
