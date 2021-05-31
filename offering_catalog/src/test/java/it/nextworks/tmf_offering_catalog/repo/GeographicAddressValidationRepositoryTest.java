package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddressValidation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GeographicAddressValidationRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private GeographicAddressValidationRepository geographicAddressValidationRepository;

    @Test
    public void whenFindById_thenReturnGeographicAddressValidation() {
        GeographicAddressValidation geographicAddressValidation = testEntityManager.persistAndFlush(new GeographicAddressValidation());

        Optional<GeographicAddressValidation> foundGeographicAddressValidation = geographicAddressValidationRepository.findById(geographicAddressValidation.getId());

        assertThat(foundGeographicAddressValidation.isPresent()).isTrue();
        assertThat(foundGeographicAddressValidation.get().getId()).isEqualTo(geographicAddressValidation.getId());
    }

    @Test
    public void whenFindByInvalidId_thenReturnVoid() {
        Optional<GeographicAddressValidation> foundGeographicAddress = geographicAddressValidationRepository.findById("invalidId");

        assertThat(foundGeographicAddress.isPresent()).isFalse();
    }

    @Test
    public void whenSave_thenCreateGeographicAddressValidation() {
        GeographicAddressValidation createdGeographicAddressValidation = geographicAddressValidationRepository.save(new GeographicAddressValidation());

        assertThat(testEntityManager.find(GeographicAddressValidation.class, createdGeographicAddressValidation.getId())).isNotNull();
    }

}
