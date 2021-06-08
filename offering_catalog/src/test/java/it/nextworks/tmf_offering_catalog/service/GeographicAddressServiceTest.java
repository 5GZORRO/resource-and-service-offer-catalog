package it.nextworks.tmf_offering_catalog.service;

import it.nextworks.tmf_offering_catalog.common.config.GeographicAddressTestContextConfiguration;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddress;
import it.nextworks.tmf_offering_catalog.repo.GeographicAddressRepository;
import it.nextworks.tmf_offering_catalog.rest.filter.GeographicAddressFilter;
import it.nextworks.tmf_offering_catalog.services.GeographicAddressService;
import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@Import(GeographicAddressTestContextConfiguration.class)
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

        when(geographicAddressRepository.findById(geographicAddressZero.getId())).thenReturn(Optional.of(geographicAddressZero));
        when(geographicAddressRepository.findAll()).thenReturn(allGeographicAddresses);
        when(geographicAddressRepository.filteredFindAll(sameCityGeographicAddressesFilter)).thenReturn((sameCityGeographicAddresses));
    }

    @Test
    @Ignore
    public void whenGet_thenGeographicAddressShouldBeFound() throws NotExistingEntityException {
        GeographicAddress foundGeographicAddress = geographicAddressService.get("0");

        assertThat(foundGeographicAddress).isNotNull();
        assertThat(foundGeographicAddress.getId()).isEqualTo("0");
        checkRepositoryFindByIdMethod("0");
    }

    @Test(expected = NotExistingEntityException.class)
    @Ignore
    public void whenGetWithInvalidId_thenExceptionShouldThrown() throws NotExistingEntityException {
        geographicAddressService.get("invalidId");
    }

    @Test
    @Ignore
    public void whenList_thenAllGeographicAddressesShouldBeFound() {
        List<GeographicAddress> geographicAddresses = geographicAddressService.list(null);

        assertThat(geographicAddresses).hasSize(3);
        assertThat(geographicAddresses).extracting(GeographicAddress::getId).containsOnly("1", "2", "3");
        checkRepositoryFindAllMethod();
    }

    @Test
    @Ignore
    public void whenListFiltered_thenFilteredGeographicAddressesShouldBeFound() {
        GeographicAddressFilter geographicAddressFilter = new GeographicAddressFilter().city("Barcelona");
        List<GeographicAddress> geographicAddresses = geographicAddressService.list(geographicAddressFilter);

        assertThat(geographicAddresses).hasSize(2);
        assertThat(geographicAddresses).extracting(GeographicAddress::getId, GeographicAddress::getCity).containsOnly(new Tuple("1", "Barcelona"), new Tuple("3", "Barcelona"));
        checkRepositoryFilteredFindAllMethod(geographicAddressFilter);
    }

    @Test
    @Ignore
    public void whenListFilteredInvalid_thenGeographicAddressesShouldNotBeFound() {
        GeographicAddressFilter geographicAddressFilter = new GeographicAddressFilter().city("Invalid city");
        List<GeographicAddress> geographicAddresses = geographicAddressService.list(geographicAddressFilter);

        assertThat(geographicAddresses).hasSize(0);
        checkRepositoryFilteredFindAllMethod(geographicAddressFilter);
    }

    private void checkRepositoryFindByIdMethod(String id) throws NotExistingEntityException {
        verify(geographicAddressRepository, VerificationModeFactory.times(1)).findById(id);
        reset(geographicAddressRepository);
    }

    private void checkRepositoryFindAllMethod() {
        verify(geographicAddressRepository, VerificationModeFactory.times(1)).findAll();
        reset(geographicAddressRepository);
    }

    private void checkRepositoryFilteredFindAllMethod(GeographicAddressFilter geographicAddressFilter) {
        verify(geographicAddressRepository, VerificationModeFactory.times(1)).filteredFindAll(geographicAddressFilter);
        reset(geographicAddressRepository);
    }

}
