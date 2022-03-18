package it.nextworks.tmf_offering_catalog.service;

import it.nextworks.tmf_offering_catalog.common.config.GeographicAddressValidationTestContextConfiguration;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddress;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddressCreate;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddressValidation;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddressValidationCreate;
import it.nextworks.tmf_offering_catalog.repo.GeographicAddressValidationRepository;
import it.nextworks.tmf_offering_catalog.repo.GeographicLocationRepository;
import it.nextworks.tmf_offering_catalog.repo.GeographicPointRepository;
import it.nextworks.tmf_offering_catalog.services.GeographicAddressValidationService;
import org.junit.Before;
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
@Import(GeographicAddressValidationTestContextConfiguration.class)
public class GeographicAddressValidationServiceTest {

    @Autowired
    private GeographicAddressValidationService geographicAddressValidationService;

    @MockBean
    private GeographicAddressValidationRepository geographicAddressValidationRepository;

    @MockBean
    private GeographicLocationRepository geographicLocationRepository;

    @MockBean
    private GeographicPointRepository geographicPointRepository;

    @Before
    public void setUp() {
        GeographicAddressValidation geographicAddressValidationZero = new GeographicAddressValidation().id("0");
        geographicAddressValidationZero.setValidGeographicAddress(new GeographicAddress());
        GeographicAddressValidation geographicAddressValidationOne = new GeographicAddressValidation().id("1");
        GeographicAddressValidation geographicAddressValidationTwo = new GeographicAddressValidation().id("2");
        GeographicAddressValidation geographicAddressValidationThree = new GeographicAddressValidation().id("3");
        List<GeographicAddressValidation> allGeographicAddressValidations = Arrays.asList(geographicAddressValidationOne, geographicAddressValidationTwo, geographicAddressValidationThree);

        when(geographicAddressValidationRepository.findByGeographicAddressValidationId(geographicAddressValidationZero.getId())).thenReturn(Optional.of(geographicAddressValidationZero));
        when(geographicAddressValidationRepository.findAll()).thenReturn(allGeographicAddressValidations);
        when(geographicAddressValidationRepository.save(any(GeographicAddressValidation.class))).thenReturn(geographicAddressValidationZero);
    }

    @Test
    public void whenGet_thenGeographicAddressValidationShouldBeFound() throws NotExistingEntityException {
        String id = "0";
        GeographicAddressValidation foundGeographicAddress = geographicAddressValidationService.get(id);

        assertThat(foundGeographicAddress).isNotNull();
        assertThat(foundGeographicAddress.getId()).isEqualTo(id);
        checkRepositoryFindByIdMethod(id);
    }

    @Test(expected = NotExistingEntityException.class)
    public void whenGetWithInvalidId_thenExceptionShouldThrown() throws NotExistingEntityException {
        geographicAddressValidationService.get("invalidId");
    }

    @Test
    public void whenList_thenAllGeographicAddressValidationsShouldBeFound() {
        List<GeographicAddressValidation> geographicAddressValidations = geographicAddressValidationService.list();

        assertThat(geographicAddressValidations).hasSize(3);
        assertThat(geographicAddressValidations).extracting(GeographicAddressValidation::getId).containsOnly("1", "2", "3");
        checkRepositoryFindAllMethod();
    }

    @Test
    public void whenCreate_thenReturnCreatedGeographicAddressValidation() {
        String id = "0";
        GeographicAddressValidationCreate geographicAddressValidationCreate = new GeographicAddressValidationCreate();
        geographicAddressValidationCreate.setSubmittedGeographicAddress(new GeographicAddressCreate());
        GeographicAddressValidation geographicAddressValidation = geographicAddressValidationService.create(geographicAddressValidationCreate);

        assertThat(geographicAddressValidation).isNotNull();
        assertThat(geographicAddressValidation.getId()).isEqualTo(id);
        checkRepositorySaveMethod();
    }

    private void checkRepositoryFindByIdMethod(String id) {
        verify(geographicAddressValidationRepository, VerificationModeFactory.times(1)).findByGeographicAddressValidationId(id);
        reset(geographicAddressValidationRepository);
    }

    private void checkRepositoryFindAllMethod() {
        verify(geographicAddressValidationRepository, VerificationModeFactory.times(1)).findAll();
        reset(geographicAddressValidationRepository);
    }

    private void checkRepositorySaveMethod() {
        verify(geographicAddressValidationRepository, VerificationModeFactory.times(1)).save(any(GeographicAddressValidation.class));
        reset(geographicAddressValidationRepository);
    }

}
