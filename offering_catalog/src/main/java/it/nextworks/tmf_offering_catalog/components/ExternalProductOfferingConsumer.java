package it.nextworks.tmf_offering_catalog.components;

import it.nextworks.tmf_offering_catalog.information_models.kafka.ExternalProductOffering;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOffering;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingStatesEnum;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingStatus;
import it.nextworks.tmf_offering_catalog.repo.ProductOfferingRepository;
import it.nextworks.tmf_offering_catalog.repo.ProductOfferingStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
public class ExternalProductOfferingConsumer {

    private static final Logger log = LoggerFactory.getLogger(ExternalProductOfferingConsumer.class);

    @Autowired
    private ProductOfferingRepository productOfferingRepository;

    @Autowired
    private ProductOfferingStatusRepository productOfferingStatusRepository;

    @KafkaListener(
            topics = "externalProductOffering",
            containerFactory = "kafkaListenerContainerFactory")
    public void listener(ExternalProductOffering externalProductOffering) {

        log.info("Receiving External Product Offering");

        if(externalProductOffering == null) {
            log.warn("Received empty External Product Offering.");
            return;
        }

        ProductOffering po = externalProductOffering.getProductOffering();
        if(po == null) {
            log.warn("Received empty Product Offering in External Product Offering.");
            return;
        }

        Optional<ProductOffering> optionalPo = productOfferingRepository.findByProductOfferingId(po.getId());
        if(optionalPo.isPresent()) {
            log.info("Received already existing in DB Product Offering.");
            return;
        }

        String id = po.getId();
        ProductOfferingStatus s = new ProductOfferingStatus()
                .catalogId(id)
                .did(externalProductOffering.getDid())
                .status(ProductOfferingStatesEnum.EXTERNAL);

        productOfferingRepository.save(po);
        productOfferingStatusRepository.save(s);

        log.info("External Product Offering " + id + " successfully stored.");
    }
}
