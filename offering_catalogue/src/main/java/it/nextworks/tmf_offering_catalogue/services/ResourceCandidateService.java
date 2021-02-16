package it.nextworks.tmf_offering_catalogue.services;

import it.nextworks.tmf_offering_catalogue.information_models.resource.ResourceCandidate;
import it.nextworks.tmf_offering_catalogue.repo.ResourceCandidateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ResourceCandidateService {

    private static final Logger log = LoggerFactory.getLogger(ResourceCandidateService.class);

    @Autowired
    private ResourceCandidateRepository resourceCandidateRepository;

    //public String storeResourceCandidate(ResourceCandidate resourceCandidate)
}
