package it.nextworks.tmf_offering_catalog.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddressValidation;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddressValidationCreate;
import it.nextworks.tmf_offering_catalog.services.GeographicAddressValidationService;
import org.junit.Before;
import org.junit.Ignore;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebAppConfiguration
public class GeographicAddressValidationControllerTest {

    private MockMvc mvc;

    @InjectMocks
    private GeographicAddressValidationController geographicAddressValidationController;

    @Mock
    private GeographicAddressValidationService geographicAddressValidationService;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(geographicAddressValidationController).build();
    }

    @Test
    @Ignore
    public void whenGet_thenReturnGeographicAddressValidation() throws Exception {
        GeographicAddressValidation geographicAddressValidation = new GeographicAddressValidation().id("123e4567-e89b-12d3-a456-426655440000");
        when(geographicAddressValidationService.get(geographicAddressValidation.getId())).thenReturn(geographicAddressValidation);

        MockHttpServletResponse response = mvc
                .perform(get("/geographicAddressManagement/v4/geographicAddressValidation/{id}", geographicAddressValidation.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(new ObjectMapper().readValue(response.getContentAsString(), GeographicAddressValidation.class)).isEqualTo(geographicAddressValidation);
        checkServiceGetMethod(1, geographicAddressValidation.getId());
    }

    @Test
    @Ignore
    public void whenGetByInvalidId_thenReturnBadRequest() throws Exception {
        String invalidId = "invalidId";
        MockHttpServletResponse response = mvc
                .perform(get("/geographicAddressManagement/v4/geographicAddressValidation/{id}", invalidId)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        checkServiceGetMethod(0, invalidId);
    }

    @Test
    @Ignore
    public void whenGetByNotExistingId_thenReturnNotFound() throws Exception {
        String notExistingId = "123e4567-e89b-12d3-a456-426655440001";
        when(geographicAddressValidationService.get(notExistingId)).thenThrow(NotExistingEntityException.class);

        MockHttpServletResponse response = mvc
                .perform(get("/geographicAddressManagement/v4/geographicAddressValidation/{id}", notExistingId)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        checkServiceGetMethod(1, notExistingId);
    }

    @Test
    @Ignore
    public void whenPost_thenReturnCreatedGeographicAddressValidation() throws Exception {
        GeographicAddressValidation geographicAddressValidation = new GeographicAddressValidation().id("123e4567-e89b-12d3-a456-426655440002");
        when(geographicAddressValidationService.create(new GeographicAddressValidationCreate())).thenReturn(geographicAddressValidation);
        MockHttpServletResponse response = mvc
                .perform(post("/geographicAddressManagement/v4/geographicAddressValidation")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(new GeographicAddressValidationCreate())))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(new ObjectMapper().readValue(response.getContentAsString(), GeographicAddressValidation.class)).isEqualTo(geographicAddressValidation);
        checkServiceCreateMethod(1, new GeographicAddressValidationCreate());
    }

    @Test
    @Ignore
    public void whenPostInvalidRequest_thenReturnBadRequest() throws Exception {
        MockHttpServletResponse response = mvc
                .perform(post("/geographicAddressManagement/v4/geographicAddressValidation")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        checkServiceCreateMethod(0, null);
    }

    private void checkServiceGetMethod(int times, String id) throws NotExistingEntityException {
        verify(geographicAddressValidationService, VerificationModeFactory.times(times)).get(id);
        reset(geographicAddressValidationService);
    }

    private void checkServiceCreateMethod(int times, GeographicAddressValidationCreate geographicAddressValidationCreate) throws NotExistingEntityException {
        verify(geographicAddressValidationService, VerificationModeFactory.times(times)).create(geographicAddressValidationCreate);
        reset(geographicAddressValidationService);
    }

}
