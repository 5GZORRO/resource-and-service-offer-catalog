package it.nextworks.tmf_offering_catalog.repo.impl;

import it.nextworks.tmf_offering_catalog.information_models.common.PlaceRef;
import it.nextworks.tmf_offering_catalog.information_models.common.PlaceRef_;
import it.nextworks.tmf_offering_catalog.information_models.common.RelatedParty;
import it.nextworks.tmf_offering_catalog.information_models.common.RelatedParty_;
import it.nextworks.tmf_offering_catalog.information_models.product.*;
import it.nextworks.tmf_offering_catalog.repo.ProductOfferingRepositoryCustom;
import it.nextworks.tmf_offering_catalog.rest.Filter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Repository
public class ProductOfferingRepositoryImpl implements ProductOfferingRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ProductOffering> filteredFindAll(Filter filter) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ProductOffering> cq = cb.createQuery(ProductOffering.class);

        Root<ProductOffering> productOfferingRoot = cq.from(ProductOffering.class);
        cq.select(productOfferingRoot).distinct(true);

        List<Predicate> predicates = new ArrayList<>();

        String category = filter.getCategory();
        if(category != null && !category.isEmpty()) {
            Join<ProductOffering, CategoryRef> categoryRefJoin = productOfferingRoot.join(ProductOffering_.category);

            predicates.add(cb.like(cb.upper(categoryRefJoin.get(CategoryRef_.name)), "%" + category.toUpperCase(Locale.ROOT) + "%"));
        }

        Float minPrice = filter.getMinPrice();
        Float maxPrice = filter.getMaxPrice();
        String currency = filter.getCurrency();
        Join<ProductOffering, ProductOfferingPriceRef> productOfferingPriceRefJoin =
                productOfferingRoot.join(ProductOffering_.productOfferingPrice);
        Root<ProductOfferingPrice> productOfferingPriceRoot = cq.from(ProductOfferingPrice.class);

        if(!predicates.isEmpty())
            predicates.add(cb.and(cb.equal(productOfferingPriceRefJoin.get(ProductOfferingPriceRef_.id),
                    productOfferingPriceRoot.get(ProductOfferingPrice_.id))));
        else
            predicates.add(cb.equal(productOfferingPriceRefJoin.get(ProductOfferingPriceRef_.id),
                    productOfferingPriceRoot.get(ProductOfferingPrice_.id)));

        predicates.add(cb.and(cb.between(productOfferingPriceRoot.get(ProductOfferingPrice_.price).get(Money_.value),
                minPrice, maxPrice)));
        if(currency != null && !currency.isEmpty()) {
            predicates.add(cb.and(cb.like(cb.upper(productOfferingPriceRoot
                    .get(ProductOfferingPrice_.price)
                    .get(Money_.unit)), "%" + currency.toUpperCase(Locale.ROOT) + "%")));
        }

        String stakeholder = filter.getStakeholder();
        if(stakeholder != null && !stakeholder.isEmpty()) {
            Join<ProductOffering, ProductSpecificationRef> productSpecificationRefJoin =
                    productOfferingRoot.join(ProductOffering_.productSpecification);
            Root<ProductSpecification> productSpecificationRoot = cq.from(ProductSpecification.class);
            Join<ProductSpecification, RelatedParty> relatedPartyJoin =
                    productSpecificationRoot.join(ProductSpecification_.relatedParty);

            if(!predicates.isEmpty())
                predicates.add(cb.and(cb.equal(productSpecificationRefJoin.get(ProductSpecificationRef_.id),
                        productSpecificationRoot.get(ProductSpecification_.id))));
            else
                predicates.add(cb.equal(productSpecificationRefJoin.get(ProductSpecificationRef_.id),
                        productSpecificationRoot.get(ProductSpecification_.id)));

            predicates.add(cb.and(cb.like(cb.upper(relatedPartyJoin.get(RelatedParty_.name)),
                    "%" + stakeholder.toUpperCase(Locale.ROOT) + "%")));
        }

        String location = filter.getLocation();
        if(location != null && !location.isEmpty()) {
            Join<ProductOffering, PlaceRef> placeRefJoin = productOfferingRoot.join(ProductOffering_.place);
            Root<GeographicAddress> geographicAddressRoot = cq.from(GeographicAddress.class);

            if(!predicates.isEmpty())
                predicates.add(cb.and(cb.equal(placeRefJoin.get(PlaceRef_.id),
                        geographicAddressRoot.get(GeographicAddress_.id))));
            else
                predicates.add(cb.equal(placeRefJoin.get(PlaceRef_.id),
                        geographicAddressRoot.get(GeographicAddress_.id)));

            predicates.add(cb.and(cb.like(cb.upper(geographicAddressRoot.get(GeographicAddress_.geographicLocation)
                    .get(GeographicLocation_.name)), "%" + location.toUpperCase(Locale.ROOT) + "%")));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }
}
