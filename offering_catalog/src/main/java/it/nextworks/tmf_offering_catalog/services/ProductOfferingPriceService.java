package it.nextworks.tmf_offering_catalog.services;

import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.common.PlaceRef;
import it.nextworks.tmf_offering_catalog.information_models.product.*;
import it.nextworks.tmf_offering_catalog.repo.ProductOfferingPriceRepository;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.threeten.bp.Instant;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ProductOfferingPriceService {

    private static final Logger log = LoggerFactory.getLogger(ProductOfferingPriceService.class);

    private static final String protocol = "http://";
    @Value("${server.hostname}")
    private String hostname;
    @Value("${server.port}")
    private String port;
    private static final String path = "/tmf-api/productCatalogManagement/v4/productOfferingPrice/";

    @Autowired
    private ProductOfferingPriceRepository productOfferingPriceRepository;

    public ProductOfferingPrice create(ProductOfferingPriceCreate productOfferingPriceCreate) {

        log.info("Received request to create a Product Offering Price.");

        final String id = UUID.randomUUID().toString();
        ProductOfferingPrice productOfferingPrice = new ProductOfferingPrice()
                .baseType(productOfferingPriceCreate.getBaseType())
                .schemaLocation(productOfferingPriceCreate.getSchemaLocation())
                .type(productOfferingPriceCreate.getType())
                .bundledPopRelationship(productOfferingPriceCreate.getBundledPopRelationship())
                .constraint(productOfferingPriceCreate.getConstraint())
                .description(productOfferingPriceCreate.getDescription())
                .href(protocol + hostname + ":" + port + path + id)
                .id(id)
                .isBundle(productOfferingPriceCreate.isIsBundle())
                .lifecycleStatus(productOfferingPriceCreate.getLifecycleStatus())
                .name(productOfferingPriceCreate.getName())
                .percentage(productOfferingPriceCreate.getPercentage())
                .place(productOfferingPriceCreate.getPlace())
                .popRelationship(productOfferingPriceCreate.getPopRelationship())
                .price(productOfferingPriceCreate.getPrice())
                .priceType(productOfferingPriceCreate.getPriceType())
                .pricingLogicAlgorithm(productOfferingPriceCreate.getPricingLogicAlgorithm())
                .prodSpecCharValueUse(productOfferingPriceCreate.getProdSpecCharValueUse())
                .productOfferingTerm(productOfferingPriceCreate.getProductOfferingTerm())
                .recurringChargePeriodLength(productOfferingPriceCreate.getRecurringChargePeriodLength())
                .recurringChargePeriodType(productOfferingPriceCreate.getRecurringChargePeriodType())
                .tax(productOfferingPriceCreate.getTax())
                .unitOfMeasure(productOfferingPriceCreate.getUnitOfMeasure())
                .validFor(productOfferingPriceCreate.getValidFor())
                .version(productOfferingPriceCreate.getVersion());

        final OffsetDateTime lastUpdate = productOfferingPriceCreate.getLastUpdate();
        if(lastUpdate != null)
            productOfferingPrice.setLastUpdate(lastUpdate.toString());

        productOfferingPriceRepository.save(productOfferingPrice);

        log.info("Product Offering Price created with id " + id + ".");

        return productOfferingPrice;
    }

    public void delete(String id) throws NotExistingEntityException {

        log.info("Received request to delete Product Offering Price with id " + id + ".");

        Optional<ProductOfferingPrice> toDelete = productOfferingPriceRepository.findByProductOfferingPriceId(id);
        if(!toDelete.isPresent())
            throw new NotExistingEntityException("Product Offering Price with id " + id + " not found in DB.");

        productOfferingPriceRepository.delete(toDelete.get());

        log.info("Product Offering Price " + id + " deleted.");
    }

    public List<ProductOfferingPrice> list() {

        log.info("Received request to retrieve all Product Offering Prices.");

        List<ProductOfferingPrice> productOfferingPrices = productOfferingPriceRepository.findAll();
        for(ProductOfferingPrice pop : productOfferingPrices) {
            pop.setBundledPopRelationship((List<BundledProductOfferingPriceRelationship>)
                    Hibernate.unproxy(pop.getBundledPopRelationship()));
            pop.setConstraint((List<ConstraintRef>) Hibernate.unproxy(pop.getConstraint()));
            pop.setPlace((List<PlaceRef>) Hibernate.unproxy(pop.getPlace()));
            pop.setPopRelationship((List<ProductOfferingPriceRelationship>)
                    Hibernate.unproxy(pop.getPopRelationship()));
            pop.setPricingLogicAlgorithm((List<PricingLogicAlgorithm>)
                    Hibernate.unproxy(pop.getPricingLogicAlgorithm()));

            pop.setProdSpecCharValueUse((List<ProductSpecificationCharacteristicValueUse>)
                    Hibernate.unproxy(pop.getProdSpecCharValueUse()));
            if(pop.getProdSpecCharValueUse() != null) {
                for (ProductSpecificationCharacteristicValueUse pscvu : pop.getProdSpecCharValueUse()) {
                    pscvu.setProductSpecCharacteristicValue((List<ProductSpecificationCharacteristicValue>)
                            Hibernate.unproxy(pscvu.getProductSpecCharacteristicValue()));

                    pscvu.setProductSpecification((ProductSpecificationRef)
                            Hibernate.unproxy(pscvu.getProductSpecification()));
                    if(pscvu.getProductSpecification() != null)
                        pscvu.getProductSpecification().setTargetProductSchema((TargetProductSchema)
                                Hibernate.unproxy(pscvu.getProductSpecification().getTargetProductSchema()));
                }
            }

            pop.setProductOfferingTerm((List<ProductOfferingTerm>) Hibernate.unproxy(pop.getProductOfferingTerm()));
            pop.setTax((List<TaxItem>) Hibernate.unproxy(pop.getTax()));
        }

        log.info("Product Offering Prices retrieved.");

        return productOfferingPrices;
    }

    public ProductOfferingPrice patch(String id, ProductOfferingPriceUpdate productOfferingPriceUpdate)
        throws NotExistingEntityException {

        log.info("Received request to patch Product Offering Price with id " + id + ".");

        Optional<ProductOfferingPrice> toUpdate = productOfferingPriceRepository.findByProductOfferingPriceId(id);
        if(!toUpdate.isPresent())
            throw new NotExistingEntityException("Product Offering Price with id " + id + " not found in DB.");

        ProductOfferingPrice productOfferingPrice = toUpdate.get();

        productOfferingPrice.setBaseType(productOfferingPriceUpdate.getBaseType());
        productOfferingPrice.setSchemaLocation(productOfferingPriceUpdate.getSchemaLocation());
        productOfferingPrice.setType(productOfferingPriceUpdate.getType());

        final List<BundledProductOfferingPriceRelationship> bundledPopRelationship =
                productOfferingPriceUpdate.getBundledPopRelationship();
        if(productOfferingPrice.getBundledPopRelationship() == null)
            productOfferingPrice.setBundledPopRelationship(bundledPopRelationship);
        else if(bundledPopRelationship != null) {
            productOfferingPrice.getBundledPopRelationship().clear();
            productOfferingPrice.getBundledPopRelationship().addAll(bundledPopRelationship);
        }
        else
            productOfferingPrice.getBundledPopRelationship().clear();

        final List<ConstraintRef> constraint = productOfferingPriceUpdate.getConstraint();
        if(productOfferingPrice.getConstraint() == null)
            productOfferingPrice.setConstraint(constraint);
        else if(constraint != null) {
            productOfferingPrice.getConstraint().clear();
            productOfferingPrice.getConstraint().addAll(constraint);
        }
        else
            productOfferingPrice.getConstraint().clear();

        productOfferingPrice.setDescription(productOfferingPriceUpdate.getDescription());
        productOfferingPrice.setIsBundle(productOfferingPriceUpdate.isIsBundle());
        productOfferingPrice.setLastUpdate(OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toString());
        productOfferingPrice.setLifecycleStatus(productOfferingPriceUpdate.getLifecycleStatus());
        productOfferingPrice.setName(productOfferingPriceUpdate.getName());
        productOfferingPrice.setPercentage(productOfferingPriceUpdate.getPercentage());

        final List<PlaceRef> place = productOfferingPriceUpdate.getPlace();
        if(productOfferingPrice.getPlace() == null)
            productOfferingPrice.setPlace(place);
        else if(place != null) {
            productOfferingPrice.getPlace().clear();
            productOfferingPrice.getPlace().addAll(place);
        }
        else
            productOfferingPrice.getPlace().clear();

        final List<ProductOfferingPriceRelationship> popRelationship = productOfferingPriceUpdate.getPopRelationship();
        if(productOfferingPrice.getPopRelationship() == null)
            productOfferingPrice.setPopRelationship(popRelationship);
        else if(popRelationship != null) {
            productOfferingPrice.getPopRelationship().clear();
            productOfferingPrice.getPopRelationship().addAll(popRelationship);
        }
        else
            productOfferingPrice.getPopRelationship().clear();

        productOfferingPrice.setPrice(productOfferingPriceUpdate.getPrice());
        productOfferingPrice.setPriceType(productOfferingPriceUpdate.getPriceType());

        final List<PricingLogicAlgorithm> pricingLogicAlgorithm = productOfferingPriceUpdate.getPricingLogicAlgorithm();
        if(productOfferingPrice.getPricingLogicAlgorithm() == null)
            productOfferingPrice.setPricingLogicAlgorithm(pricingLogicAlgorithm);
        else if(pricingLogicAlgorithm != null) {
            productOfferingPrice.getPricingLogicAlgorithm().clear();
            productOfferingPrice.getPricingLogicAlgorithm().addAll(pricingLogicAlgorithm);
        }
        else
            productOfferingPrice.getPricingLogicAlgorithm().clear();

        final List<ProductSpecificationCharacteristicValueUse> prodSpecCharValueUse =
                productOfferingPriceUpdate.getProdSpecCharValueUse();
        if(productOfferingPrice.getProdSpecCharValueUse() == null)
            productOfferingPrice.setProdSpecCharValueUse(prodSpecCharValueUse);
        else if(prodSpecCharValueUse != null) {
            productOfferingPrice.getProdSpecCharValueUse().clear();
            productOfferingPrice.getProdSpecCharValueUse().addAll(prodSpecCharValueUse);
        }
        else
            productOfferingPrice.getProdSpecCharValueUse().clear();

        final List<ProductOfferingTerm> productOfferingTerm = productOfferingPriceUpdate.getProductOfferingTerm();
        if(productOfferingPrice.getProductOfferingTerm() == null)
            productOfferingPrice.setProductOfferingTerm(productOfferingTerm);
        else if(productOfferingTerm != null) {
            productOfferingPrice.getProductOfferingTerm().clear();
            productOfferingPrice.getProductOfferingTerm().addAll(productOfferingTerm);
        }
        else
            productOfferingPrice.getProductOfferingTerm().clear();

        productOfferingPrice.setRecurringChargePeriodLength(productOfferingPriceUpdate.getRecurringChargePeriodLength());
        productOfferingPrice.setRecurringChargePeriodType(productOfferingPriceUpdate.getRecurringChargePeriodType());

        final List<TaxItem> tax = productOfferingPriceUpdate.getTax();
        if(productOfferingPrice.getTax() == null)
            productOfferingPrice.setTax(tax);
        else if(tax != null) {
            productOfferingPrice.getTax().clear();
            productOfferingPrice.getTax().addAll(tax);
        }
        else
            productOfferingPrice.getTax().clear();

        productOfferingPrice.setUnitOfMeasure(productOfferingPriceUpdate.getUnitOfMeasure());
        productOfferingPrice.setValidFor(productOfferingPriceUpdate.getValidFor());
        productOfferingPrice.setVersion(productOfferingPriceUpdate.getVersion());

        productOfferingPriceRepository.save(productOfferingPrice);

        log.info("Product Offering Price " + id + " patched.");

        return productOfferingPrice;
    }

    public ProductOfferingPrice get(String id) throws NotExistingEntityException {

        log.info("Received request to retrieve Product Offering Price with id " + id + ".");

        Optional<ProductOfferingPrice> retrieved = productOfferingPriceRepository.findByProductOfferingPriceId(id);
        if(!retrieved.isPresent())
            throw new NotExistingEntityException("Product Offering Price with id " + id + " not found in DB.");

        ProductOfferingPrice pop = retrieved.get();

        pop.setBundledPopRelationship((List<BundledProductOfferingPriceRelationship>)
                Hibernate.unproxy(pop.getBundledPopRelationship()));
        pop.setConstraint((List<ConstraintRef>) Hibernate.unproxy(pop.getConstraint()));
        pop.setPlace((List<PlaceRef>) Hibernate.unproxy(pop.getPlace()));
        pop.setPopRelationship((List<ProductOfferingPriceRelationship>)
                Hibernate.unproxy(pop.getPopRelationship()));
        pop.setPricingLogicAlgorithm((List<PricingLogicAlgorithm>)
                Hibernate.unproxy(pop.getPricingLogicAlgorithm()));

        pop.setProdSpecCharValueUse((List<ProductSpecificationCharacteristicValueUse>)
                Hibernate.unproxy(pop.getProdSpecCharValueUse()));
        if(pop.getProdSpecCharValueUse() != null) {
            for (ProductSpecificationCharacteristicValueUse pscvu : pop.getProdSpecCharValueUse()) {
                pscvu.setProductSpecCharacteristicValue((List<ProductSpecificationCharacteristicValue>)
                        Hibernate.unproxy(pscvu.getProductSpecCharacteristicValue()));

                pscvu.setProductSpecification((ProductSpecificationRef)
                        Hibernate.unproxy(pscvu.getProductSpecification()));
                if(pscvu.getProductSpecification() != null)
                    pscvu.getProductSpecification().setTargetProductSchema((TargetProductSchema)
                            Hibernate.unproxy(pscvu.getProductSpecification().getTargetProductSchema()));
            }
        }

        pop.setProductOfferingTerm((List<ProductOfferingTerm>) Hibernate.unproxy(pop.getProductOfferingTerm()));
        pop.setTax((List<TaxItem>) Hibernate.unproxy(pop.getTax()));

        log.info("Product Offering Price " + id + " retrieved.");

        return pop;
    }
}
