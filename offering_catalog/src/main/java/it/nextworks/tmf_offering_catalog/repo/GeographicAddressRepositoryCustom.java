package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddress;
import it.nextworks.tmf_offering_catalog.rest.filter.GeographicAddressFilter;

import java.util.List;

public interface GeographicAddressRepositoryCustom {

    List<GeographicAddress> filteredFindAll(GeographicAddressFilter geographicAddressFilter);

}
