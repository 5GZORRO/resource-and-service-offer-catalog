package it.nextworks.tmf_offering_catalog.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddress;
import it.nextworks.tmf_offering_catalog.rest.filter.GeographicAddressFilter;
import it.nextworks.tmf_offering_catalog.services.GeographicAddressService;
import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebAppConfiguration
public class GeographicAddressControllerTest {

    private MockMvc mvc;

    @InjectMocks
    private GeographicAddressController geographicAddressController;

    @Mock
    private GeographicAddressService geographicAddressService;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(geographicAddressController).build();
    }

    @Test
    public void whenGet_thenReturnGeographicAddress() throws Exception {
        GeographicAddress geographicAddress = new GeographicAddress().id("123e4567-e89b-12d3-a456-426655440000");
        when(geographicAddressService.get(geographicAddress.getId())).thenReturn(geographicAddress);

        MockHttpServletResponse response = mvc
                .perform(get("/geographicAddressManagement/v4/geographicAddress/{id}", geographicAddress.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(new ObjectMapper().readValue(response.getContentAsString(), GeographicAddress.class)).isEqualTo(geographicAddress);
        checkServiceGetMethod(1, geographicAddress.getId());
    }

    @Test
    public void whenGetByInvalidId_thenReturnBadRequest() throws Exception {
        String invalidId = "invalidId";
        MockHttpServletResponse response = mvc
                .perform(get("/geographicAddressManagement/v4/geographicAddress/{id}", invalidId)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        checkServiceGetMethod(0, invalidId);
    }

    @Test
    public void whenGetByNotExistingId_thenReturnNotFound() throws Exception {
        String notExistingId = "123e4567-e89b-12d3-a456-426655440001";
        when(geographicAddressService.get(notExistingId)).thenThrow(NotExistingEntityException.class);

        MockHttpServletResponse response = mvc
                .perform(get("/geographicAddressManagement/v4/geographicAddress/{id}", notExistingId)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        checkServiceGetMethod(1, notExistingId);
    }

    @Test
    public void whenGetList_thenReturnAllGeographicAddresses() throws Exception {
        List<GeographicAddress> geographicAddresses = Arrays.asList(
                new GeographicAddress().id("1"),
                new GeographicAddress().id("2")
        );
        when(geographicAddressService.list(new GeographicAddressFilter())).thenReturn(geographicAddresses);

        MockHttpServletResponse response = mvc
                .perform(get("/geographicAddressManagement/v4/geographicAddress")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        System.out.println(response.getContentAsString());
        assertThat(new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<List<GeographicAddress>>() {
        })).hasSize(2).extracting(GeographicAddress::getId).containsOnly("1", "2");
        checkServiceListMethod(new GeographicAddressFilter());
    }

    @Test
    public void whenGetListFiltered_thenReturnGeographicAddresses() throws Exception {
        GeographicAddress geographicAddress = new GeographicAddress().id("1").city("Barcelona");
        GeographicAddressFilter geographicAddressFilter = new GeographicAddressFilter().city(geographicAddress.getCity());
        when(geographicAddressService.list(geographicAddressFilter)).thenReturn(Collections.singletonList(geographicAddress));

        MockHttpServletResponse response = mvc
                .perform(get("/geographicAddressManagement/v4/geographicAddress?")
                        .param("city", geographicAddress.getCity())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        System.out.println(response.getContentAsString());
        assertThat(new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<List<GeographicAddress>>() {
        })).hasSize(1).extracting(GeographicAddress::getId, GeographicAddress::getCity).containsOnly(new Tuple(geographicAddress.getId(), geographicAddress.getCity()));
        checkServiceListMethod(geographicAddressFilter);
    }

    private void checkServiceGetMethod(int times, String id) throws NotExistingEntityException {
        verify(geographicAddressService, VerificationModeFactory.times(times)).get(id);
        reset(geographicAddressService);
    }

    private void checkServiceListMethod(GeographicAddressFilter geographicAddressFilter) throws NotExistingEntityException {
        verify(geographicAddressService, VerificationModeFactory.times(1)).list(geographicAddressFilter);
        reset(geographicAddressService);
    }

}
