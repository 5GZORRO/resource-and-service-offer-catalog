package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddress;
import it.nextworks.tmf_offering_catalog.rest.filter.GeographicAddressFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GeographicAddressRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private GeographicAddressRepository geographicAddressRepository;

    @Test
    public void whenFindById_thenReturnGeographicAddress() {
        GeographicAddress geographicAddress = testEntityManager.persistAndFlush(new GeographicAddress());

        Optional<GeographicAddress> foundGeographicAddress = geographicAddressRepository.findById(geographicAddress.getId());

        assertThat(foundGeographicAddress.isPresent()).isTrue();
        assertThat(foundGeographicAddress.get().getId()).isEqualTo(geographicAddress.getId());
    }

    @Test
    public void whenInvalidId_thenReturnVoid() {
        Optional<GeographicAddress> foundGeographicAddress = geographicAddressRepository.findById("invalidId");

        assertThat(foundGeographicAddress.isPresent()).isFalse();
    }

    @Test
    public void givenSetOfGeographicAddresses_whenFindByLocality_thenReturnGeographicAddresses() {
        GeographicAddress geographicAddress = testEntityManager.persistAndFlush(new GeographicAddress().locality("Montjuic"));
        testEntityManager.persistAndFlush(new GeographicAddress().locality("Harlem"));
        testEntityManager.persistAndFlush(new GeographicAddress().locality(geographicAddress.getLocality()));
        GeographicAddressFilter geographicAddressFilter = new GeographicAddressFilter().locality(geographicAddress.getLocality());

        List<GeographicAddress> foundGeographicAddresses = geographicAddressRepository.filteredFindAll(geographicAddressFilter);

        assertThat(foundGeographicAddresses).hasSize(2);
        assertThat(foundGeographicAddresses).extracting(GeographicAddress::getLocality).containsOnly(geographicAddress.getLocality());
    }

    @Test
    public void givenSetOfGeographicAddresses_whenFindByCity_thenReturnGeographicAddresses() {
        GeographicAddress geographicAddress = testEntityManager.persistAndFlush(new GeographicAddress().city("Barcelona"));
        testEntityManager.persistAndFlush(new GeographicAddress().city("New York"));
        testEntityManager.persistAndFlush(new GeographicAddress().city(geographicAddress.getCity()));
        GeographicAddressFilter geographicAddressFilter = new GeographicAddressFilter().city(geographicAddress.getCity());

        List<GeographicAddress> foundGeographicAddresses = geographicAddressRepository.filteredFindAll(geographicAddressFilter);

        assertThat(foundGeographicAddresses).hasSize(2);
        assertThat(foundGeographicAddresses).extracting(GeographicAddress::getCity).containsOnly(geographicAddress.getCity());
    }

    @Test
    public void givenSetOfGeographicAddresses_whenFindByCountry_thenReturnGeographicAddresses() {
        GeographicAddress geographicAddress = testEntityManager.persistAndFlush(new GeographicAddress().country("Spain"));
        testEntityManager.persistAndFlush(new GeographicAddress().country("UEA"));
        testEntityManager.persistAndFlush(new GeographicAddress().country(geographicAddress.getCountry()));
        GeographicAddressFilter geographicAddressFilter = new GeographicAddressFilter().country(geographicAddress.getCountry());

        List<GeographicAddress> foundGeographicAddresses = geographicAddressRepository.filteredFindAll(geographicAddressFilter);

        assertThat(foundGeographicAddresses).hasSize(2);
        assertThat(foundGeographicAddresses).extracting(GeographicAddress::getCountry).containsOnly(geographicAddress.getCountry());
    }

    @Test
    public void givenSetOfGeographicAddresses_whenFindByPostcode_thenReturnGeographicAddresses() {
        GeographicAddress geographicAddress = testEntityManager.persistAndFlush(new GeographicAddress().postcode("08000"));
        testEntityManager.persistAndFlush(new GeographicAddress().postcode("01000"));
        testEntityManager.persistAndFlush(new GeographicAddress().postcode(geographicAddress.getPostcode()));
        GeographicAddressFilter geographicAddressFilter = new GeographicAddressFilter().city(geographicAddress.getPostcode());

        List<GeographicAddress> foundGeographicAddresses = geographicAddressRepository.filteredFindAll(geographicAddressFilter);

        assertThat(foundGeographicAddresses).hasSize(2);
        assertThat(foundGeographicAddresses).extracting(GeographicAddress::getPostcode).containsOnly(geographicAddress.getPostcode());
    }

    @Test
    public void givenSetOfGeographicAddresses_whenFindAll_thenReturnAllGeographicAddresses() {
        GeographicAddress geographicAddressOne = testEntityManager.persistAndFlush(new GeographicAddress());
        GeographicAddress geographicAddressTwo = testEntityManager.persistAndFlush(new GeographicAddress());
        GeographicAddress geographicAddressThree = testEntityManager.persistAndFlush(new GeographicAddress());

        List<GeographicAddress> foundGeographicAddress = geographicAddressRepository.findAll();

        assertThat(foundGeographicAddress).hasSize(3);
        assertThat(foundGeographicAddress).extracting(GeographicAddress::getId).containsOnly(
                geographicAddressOne.getId(),
                geographicAddressTwo.getId(),
                geographicAddressThree.getId()
        );
    }

}
