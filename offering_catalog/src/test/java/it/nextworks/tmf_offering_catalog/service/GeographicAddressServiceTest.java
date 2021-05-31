package it.nextworks.tmf_offering_catalog.service;

import it.nextworks.tmf_offering_catalog.common.config.GeographicAddressServiceTestContextConfiguration;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddress;
import it.nextworks.tmf_offering_catalog.repo.GeographicAddressRepository;
import it.nextworks.tmf_offering_catalog.rest.filter.GeographicAddressFilter;
import it.nextworks.tmf_offering_catalog.services.GeographicAddressService;
import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@Import(GeographicAddressServiceTestContextConfiguration.class)
public class GeographicAddressServiceTest {

    @Autowired
    private GeographicAddressService geographicAddressService;

    @MockBean
    private GeographicAddressRepository geographicAddressRepository;

    @Before
    public void setUp() {
        GeographicAddress geographicAddressZero = new GeographicAddress().id("0");
        GeographicAddress geographicAddressOne = new GeographicAddress().id("1").locality("Montjuic").postcode("08000").city("Barcelona").country("Spain");
        GeographicAddress geographicAddressTwo = new GeographicAddress().id("2").locality("Harlem").postcode("01000").city("New York City").country("USA");
        GeographicAddress geographicAddressThree = new GeographicAddress().id("3").locality("Raval").postcode("08001").city("Barcelona").country("Spain");
        List<GeographicAddress> allGeographicAddresses = Arrays.asList(geographicAddressOne, geographicAddressTwo, geographicAddressThree);
        List<GeographicAddress> sameCityGeographicAddresses = Arrays.asList(geographicAddressOne, geographicAddressThree);
        GeographicAddressFilter sameCityGeographicAddressesFilter = new GeographicAddressFilter().city(geographicAddressOne.getCity());

        Mockito.when(geographicAddressRepository.findById(geographicAddressZero.getId())).thenReturn(Optional.of(geographicAddressZero));
        Mockito.when(geographicAddressRepository.findAll()).thenReturn(allGeographicAddresses);
        Mockito.when(geographicAddressRepository.filteredFindAll(sameCityGeographicAddressesFilter)).thenReturn((sameCityGeographicAddresses));
    }

    @Test
    public void whenGet_thenGeographicAddressShouldBeFound() throws NotExistingEntityException {
        GeographicAddress foundGeographicAddress = geographicAddressService.get("0");

        assertThat(foundGeographicAddress).isNotNull();
        assertThat(foundGeographicAddress.getId()).isEqualTo("0");
    }

    @Test(expected = NotExistingEntityException.class)
    public void whenGetWithInvalidId_thenExceptionShouldThrown() throws NotExistingEntityException {
        geographicAddressService.get("invalidId");
    }

    @Test
    public void whenList_thenAllGeographicAddressesShouldBeFound() {
        List<GeographicAddress> geographicAddresses = geographicAddressService.list(null);

        assertThat(geographicAddresses).hasSize(3);
        assertThat(geographicAddresses).extracting(GeographicAddress::getId).containsOnly("1", "2", "3");
    }

    @Test
    public void whenListFiltered_thenFilteredGeographicAddressesShouldBeFound() {
        List<GeographicAddress> geographicAddresses = geographicAddressService.list(new GeographicAddressFilter().city("Barcelona"));

        assertThat(geographicAddresses).hasSize(2);
        assertThat(geographicAddresses).extracting(GeographicAddress::getId, GeographicAddress::getCity).containsOnly(new Tuple("1", "Barcelona"), new Tuple("3", "Barcelona"));
    }

    @Test
    public void whenListFilteredInvalid_thenGeographicAddressesShouldNotBeFound() {
        List<GeographicAddress> geographicAddresses = geographicAddressService.list(new GeographicAddressFilter().city("Invalid city"));

        assertThat(geographicAddresses).hasSize(0);
    }


}
