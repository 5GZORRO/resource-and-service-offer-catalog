package it.nextworks.tmf_offering_catalog.repo;

import it.nextworks.tmf_offering_catalog.information_models.party.StakeholderPlatformInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StakeholderPlatformInfoRepository extends JpaRepository<StakeholderPlatformInfo, String> {
    List<StakeholderPlatformInfo> findAll();
}
