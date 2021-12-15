package it.nextworks.tmf_offering_catalog.repo.impl;

import it.nextworks.tmf_offering_catalog.information_models.product.*;
import it.nextworks.tmf_offering_catalog.repo.GeographicAddressRepositoryCustom;
import it.nextworks.tmf_offering_catalog.rest.filter.GeographicAddressFilter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GeographicAddressRepositoryImpl implements GeographicAddressRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
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

    public List<GeographicAddress> findGeographicAddressByCoordinates(String x, String y) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GeographicAddress> cq = cb.createQuery(GeographicAddress.class);

        Root<GeographicAddress> geographicAddressRoot = cq.from(GeographicAddress.class);
        cq.select(geographicAddressRoot).distinct(true);

        Join<GeographicAddress, GeographicLocation> geographicLocationJoin = geographicAddressRoot.join(GeographicAddress_.geographicLocation);
        Join<GeographicLocation, GeographicPoint> geographicPointJoin = geographicLocationJoin.join(GeographicLocation_.geometry);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.like(geographicPointJoin.get(GeographicPoint_.x), x));
        predicates.add(cb.and(cb.like(geographicPointJoin.get(GeographicPoint_.y), y)));

        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }
}
