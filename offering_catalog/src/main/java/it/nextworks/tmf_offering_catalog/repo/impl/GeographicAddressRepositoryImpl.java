package it.nextworks.tmf_offering_catalog.repo.impl;

import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddress;
import it.nextworks.tmf_offering_catalog.repo.GeographicAddressRepositoryCustom;
import it.nextworks.tmf_offering_catalog.rest.filter.GeographicAddressFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class GeographicAddressRepositoryImpl implements GeographicAddressRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    public List<GeographicAddress> filteredFindAll(GeographicAddressFilter geographicAddressFilter) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<GeographicAddress> query = criteriaBuilder.createQuery(GeographicAddress.class);
        Root<GeographicAddress> geographicAddresses = query.from(GeographicAddress.class);

        List<Predicate> predicates = new ArrayList<>();

        geographicAddressFilter.getAllAttributes().forEach((attribute, value) -> {
            if (value != null) {
                addAndPredicate(criteriaBuilder, predicates, criteriaBuilder.equal(geographicAddresses.get(attribute), value));
            }
        });

        query.select(geographicAddresses).where(predicates.toArray(new Predicate[0]));
        return em.createQuery(query).getResultList();
    }

    private void addAndPredicate(CriteriaBuilder criteriaBuilder, List<Predicate> predicates, Predicate newPredicate) {
        if (predicates.isEmpty()) {
            predicates.add(newPredicate);
        } else {
            predicates.add(criteriaBuilder.and(newPredicate));
        }
    }

}
