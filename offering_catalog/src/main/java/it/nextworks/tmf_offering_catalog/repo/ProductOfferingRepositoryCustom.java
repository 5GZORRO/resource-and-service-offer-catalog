package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.ProductOffering;
import it.nextworks.tmf_offering_catalog.rest.Filter;

import java.util.List;

public interface ProductOfferingRepositoryCustom {
    List<ProductOffering> filteredFindAll(Filter filter);
}
