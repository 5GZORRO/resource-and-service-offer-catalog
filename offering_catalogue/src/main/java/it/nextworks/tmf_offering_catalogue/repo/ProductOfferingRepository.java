package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.ResourceCandidateRef;
import it.nextworks.tmf_offering_catalogue.information_models.TimePeriod;
import it.nextworks.tmf_offering_catalogue.information_models.product.ProductOffering;
import it.nextworks.tmf_offering_catalogue.information_models.product.ProductSpecificationRef;
import it.nextworks.tmf_offering_catalogue.information_models.product.SLARef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductOfferingRepository extends JpaRepository<ProductOffering, String> {

    List<ProductOffering> findByDescription(String description);

    Optional<ProductOffering> findByHref(String href);

    Optional<ProductOffering> findById(String id);

    List<ProductOffering> findByIsBundle(Boolean isBundle);

    List<ProductOffering> findByIsSellable(Boolean isSellable);

    List<ProductOffering> findByLastUpdate(String lastUpdate);

    List<ProductOffering> findByLifecycleStatus(String lifecycleStatus);

    List<ProductOffering> findByName(String name);

    Optional<ProductOffering> findByProductSpecification(ProductSpecificationRef productSpecification);

    Optional<ProductOffering> findByResourceCandidate(ResourceCandidateRef resourceCandidate);

    Optional<ProductOffering> findByServiceLevelAgreement(SLARef serviceLevelAgreement);

    List<ProductOffering> findByStatusReason(String statusReason);

    List<ProductOffering> findByValidFor(TimePeriod validFor);

    List<ProductOffering> findByVersion(String version);
}
