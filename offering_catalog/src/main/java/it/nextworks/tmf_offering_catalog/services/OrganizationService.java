package it.nextworks.tmf_offering_catalog.services;

import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.common.exception.StakeholderAlreadyRegisteredException;
import it.nextworks.tmf_offering_catalog.information_models.common.AttachmentRefOrValue;
import it.nextworks.tmf_offering_catalog.information_models.common.RelatedParty;
import it.nextworks.tmf_offering_catalog.information_models.common.TimePeriod;
import it.nextworks.tmf_offering_catalog.information_models.party.*;
import it.nextworks.tmf_offering_catalog.repo.OrganizationRepository;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class OrganizationService {

    private static final Logger log = LoggerFactory.getLogger(OrganizationService.class);

    private static final String protocol = "http://";
    @Value("${server.hostname}")
    private String hostname;
    @Value("${server.port}")
    private String port;
    private static final String path = "/tmf-api/party/v4/organization/";

    @Autowired
    private OrganizationRepository organizationRepository;

    public Organization create(OrganizationCreate organizationCreate) throws StakeholderAlreadyRegisteredException {

        log.info("Received request to create an Organization.");

        if(organizationRepository.findAll().size() == 1)
            throw new StakeholderAlreadyRegisteredException("Stakeholder already registered.");

        final String id = UUID.randomUUID().toString();
        Organization organization = new Organization()
                .baseType(organizationCreate.getBaseType())
                .schemaLocation(organizationCreate.getSchemaLocation())
                .type(organizationCreate.getType())
                .contactMedium(organizationCreate.getContactMedium())
                .creditRating(organizationCreate.getCreditRating())
                .existsDuring(organizationCreate.getExistsDuring())
                .externalReference(organizationCreate.getExternalReference())
                .href(protocol + hostname + ":" + port + path + id)
                .id(id)
                .isHeadOffice(organizationCreate.isIsHeadOffice())
                .isLegalEntity(organizationCreate.isIsLegalEntity())
                .name(organizationCreate.getName())
                .nameType(organizationCreate.getNameType())
                .organizationChildRelationship(organizationCreate.getOrganizationChildRelationship())
                .organizationIdentification(organizationCreate.getOrganizationIdentification())
                .organizationParentRelationship(organizationCreate.getOrganizationParentRelationship())
                .organizationType(organizationCreate.getOrganizationType())
                .otherName(organizationCreate.getOtherName())
                .partyCharacteristic(organizationCreate.getPartyCharacteristic())
                .relatedParty(organizationCreate.getRelatedParty())
                .status(organizationCreate.getStatus())
                .taxExemptionCertificate(organizationCreate.getTaxExemptionCertificate())
                .tradingName(organizationCreate.getTradingName());

        organizationRepository.save(organization);

        //log.info("Requesting stakeholder registration via CommunicationService to ID&P.");
        // TODO: register stakeholder via ID&P in order to receive credential (stakeholder did and token)
        //log.info("Stakeholder registration successfully completed via ID&P.");

        log.info("Organization created with id " + id + ".");

        return organization;
    }

    public void delete(String id) throws NotExistingEntityException {

        log.info("Received request to delete Organization with id " + id + ".");

        Optional<Organization> toDelete = organizationRepository.findByOrganizationId(id);
        if(!toDelete.isPresent())
            throw new NotExistingEntityException("Organization with id " + id + " not found in DB.");


         // TODO: revoke stakeholder registration/credential?
        organizationRepository.delete(toDelete.get());

        log.info("Organization " + id + " deleted.");
    }

    public Organization patch(String id, OrganizationUpdate organizationUpdate) throws NotExistingEntityException {

        log.info("Received request to patch Organization with id " + id + ".");

        Optional<Organization> toUpdate = organizationRepository.findByOrganizationId(id);
        if(!toUpdate.isPresent())
            throw new NotExistingEntityException("Organization with id " + id + " not found in DB.");

        Organization organization = toUpdate.get();

        // TODO: revoke stakeholder registration/credential?

        final String baseType = organizationUpdate.getBaseType();
        if(baseType != null)
            organization.setBaseType(baseType);

        final String schemaLocation = organizationUpdate.getSchemaLocation();
        if(schemaLocation != null)
            organization.setSchemaLocation(schemaLocation);

        final String type = organizationUpdate.getType();
        if(type != null)
            organization.setType(type);

        final List<ContactMedium> contactMedium = organizationUpdate.getContactMedium();
        if(contactMedium != null) {
            if(organization.getContactMedium() != null) {
                organization.getContactMedium().clear();
                organization.getContactMedium().addAll(contactMedium);
            }
            else
                organization.setContactMedium(contactMedium);
        }
        else {
            organization.setContactMedium((List<ContactMedium>) Hibernate.unproxy(organization.getContactMedium()));
            if(organization.getContactMedium() != null) {
                for(ContactMedium cm : organization.getContactMedium())
                    cm.setCharacteristic((MediumCharacteristic) Hibernate.unproxy(cm.getCharacteristic()));
            }
        }

        final List<PartyCreditProfile> creditRating = organizationUpdate.getCreditRating();
        if(creditRating != null) {
            if(organization.getCreditRating() != null) {
                organization.getCreditRating().clear();
                organization.getCreditRating().addAll(creditRating);
            }
            else
                organization.setCreditRating(creditRating);
        }
        else
            organization.setCreditRating((List<PartyCreditProfile>) Hibernate.unproxy(organization.getCreditRating()));

        final TimePeriod existsDuring = organizationUpdate.getExistsDuring();
        if(existsDuring != null)
            organization.setExistsDuring(existsDuring);

        final List<ExternalReference> externalReference = organizationUpdate.getExternalReference();
        if(externalReference != null) {
            if(organization.getExternalReference() != null) {
                organization.getExternalReference().clear();
                organization.getExternalReference().addAll(externalReference);
            }
            else
                organization.setExternalReference(externalReference);
        }
        else
            organization.setExternalReference((List<ExternalReference>)
                    Hibernate.unproxy(organization.getExternalReference()));

        final Boolean isHeadOffice = organizationUpdate.isIsHeadOffice();
        if(isHeadOffice != null)
            organization.setIsHeadOffice(isHeadOffice);

        final Boolean isLegalEntity = organizationUpdate.isIsLegalEntity();
        if(isLegalEntity != null)
            organization.setIsLegalEntity(isLegalEntity);

        final String name = organizationUpdate.getName();
        if(name != null)
            organization.setName(name);

        final String nameType = organizationUpdate.getNameType();
        if(nameType != null)
            organization.setNameType(nameType);

        final List<OrganizationChildRelationship> organizationChildRelationship =
                organizationUpdate.getOrganizationChildRelationship();
        if(organizationChildRelationship != null) {
            if(organization.getOrganizationChildRelationship() != null) {
                organization.getOrganizationChildRelationship().clear();
                organization.getOrganizationChildRelationship().addAll(organizationChildRelationship);
            }
            else
                organization.setOrganizationChildRelationship(organizationChildRelationship);
        }
        else {
            organization.setOrganizationChildRelationship((List<OrganizationChildRelationship>)
                    Hibernate.unproxy(organization.getOrganizationChildRelationship()));
            if(organization.getOrganizationChildRelationship() != null) {
                for(OrganizationChildRelationship ocr : organization.getOrganizationChildRelationship())
                    ocr.setOrganization((OrganizationRef) Hibernate.unproxy(ocr.getOrganization()));
            }
        }

        final List<OrganizationIdentification> organizationIdentification =
                organizationUpdate.getOrganizationIdentification();
        if(organizationIdentification != null) {
            if(organization.getOrganizationIdentification() != null) {
                organization.getOrganizationIdentification().clear();
                organization.getOrganizationIdentification().addAll(organizationIdentification);
            }
            else
                organization.setOrganizationIdentification(organizationIdentification);
        }
        else {
            organization.setOrganizationIdentification((List<OrganizationIdentification>)
                    Hibernate.unproxy(organization.getOrganizationIdentification()));
            if(organization.getOrganizationIdentification() != null) {
                for(OrganizationIdentification oi : organization.getOrganizationIdentification())
                    oi.setAttachment((AttachmentRefOrValue) Hibernate.unproxy(oi.getAttachment()));
            }
        }

        final OrganizationParentRelationship organizationParentRelationship =
                organizationUpdate.getOrganizationParentRelationship();
        if(organizationParentRelationship != null)
            organization.setOrganizationParentRelationship(organizationParentRelationship);
        else {
            organization.setOrganizationParentRelationship((OrganizationParentRelationship)
                    Hibernate.unproxy(organization.getOrganizationParentRelationship()));
            if(organization.getOrganizationParentRelationship() != null)
                organization.getOrganizationParentRelationship().setOrganization((OrganizationRef)
                        Hibernate.unproxy(organization.getOrganizationParentRelationship().getOrganization()));
        }

        final String organizationType = organizationUpdate.getOrganizationType();
        if(organizationType != null)
            organization.setOrganizationType(organizationType);

        final List<OtherNameOrganization> otherName = organizationUpdate.getOtherName();
        if(otherName != null) {
            if(organization.getOtherName() != null) {
                organization.getOtherName().clear();
                organization.getOtherName().addAll(otherName);
            }
            else
                organization.setOtherName(otherName);
        }
        else
            organization.setOtherName((List<OtherNameOrganization>) Hibernate.unproxy(organization.getOtherName()));

        final List<Characteristic> partyCharacteristic = organizationUpdate.getPartyCharacteristic();
        if(partyCharacteristic != null) {
            if(organization.getPartyCharacteristic() != null) {
                organization.getPartyCharacteristic().clear();
                organization.getPartyCharacteristic().addAll(partyCharacteristic);
            }
            else
                organization.setPartyCharacteristic(partyCharacteristic);
        }
        else
            organization.setPartyCharacteristic((List<Characteristic>)
                    Hibernate.unproxy(organization.getPartyCharacteristic()));

        final List<RelatedParty> relatedParty = organizationUpdate.getRelatedParty();
        if(relatedParty != null) {
            if(organization.getRelatedParty() != null) {
                organization.getRelatedParty().clear();
                organization.getRelatedParty().addAll(relatedParty);
            }
            else
                organization.setRelatedParty(relatedParty);
        }
        else
            organization.setRelatedParty((List<RelatedParty>) Hibernate.unproxy(organization.getRelatedParty()));

        final PartyStatusEnum status = organizationUpdate.getStatus();
        if(status != null)
            organization.setStatus(status);

        final List<TaxExemptionCertificate> taxExemptionCertificate = organizationUpdate.getTaxExemptionCertificate();
        if(taxExemptionCertificate != null) {
            if(organization.getTaxExemptionCertificate() != null) {
                organization.getTaxExemptionCertificate().clear();
                organization.getTaxExemptionCertificate().addAll(taxExemptionCertificate);
            }
            else
                organization.setTaxExemptionCertificate(taxExemptionCertificate);
        }
        else {
            organization.setTaxExemptionCertificate((List<TaxExemptionCertificate>)
                    Hibernate.unproxy(organization.getTaxExemptionCertificate()));
            if(organization.getTaxExemptionCertificate() != null) {
                for(TaxExemptionCertificate tec : organization.getTaxExemptionCertificate()) {
                    tec.setAttachment((AttachmentRefOrValue) Hibernate.unproxy(tec.getAttachment()));
                    tec.setTaxDefinition((List<TaxDefinition>) Hibernate.unproxy(tec.getTaxDefinition()));
                }
            }
        }

        final String tradingName = organizationUpdate.getTradingName();
        if(tradingName != null)
            organization.setTradingName(tradingName);

        organizationRepository.save(organization);

        log.info("Organization " + id + " patched.");

        return organization;
    }

    public Organization get(String id) throws NotExistingEntityException {

        log.info("Received request to retrieve Organization with id " + id + ".");

        Optional<Organization> retrieved = organizationRepository.findByOrganizationId(id);
        if(!retrieved.isPresent())
            throw new NotExistingEntityException("Organization with id " + id + " not found in DB.");

        Organization o = retrieved.get();

        o.setContactMedium((List<ContactMedium>) Hibernate.unproxy(o.getContactMedium()));
        if(o.getContactMedium() != null) {
            for(ContactMedium cm : o.getContactMedium())
                cm.setCharacteristic((MediumCharacteristic) Hibernate.unproxy(cm.getCharacteristic()));
        }

        o.setCreditRating((List<PartyCreditProfile>) Hibernate.unproxy(o.getCreditRating()));
        o.setExternalReference((List<ExternalReference>) Hibernate.unproxy(o.getExternalReference()));

        o.setOrganizationChildRelationship((List<OrganizationChildRelationship>)
                Hibernate.unproxy(o.getOrganizationChildRelationship()));
        if(o.getOrganizationChildRelationship() != null) {
            for(OrganizationChildRelationship ocr : o.getOrganizationChildRelationship())
                ocr.setOrganization((OrganizationRef) Hibernate.unproxy(ocr.getOrganization()));
        }

        o.setOrganizationIdentification((List<OrganizationIdentification>)
                Hibernate.unproxy(o.getOrganizationIdentification()));
        if(o.getOrganizationIdentification() != null) {
            for(OrganizationIdentification oi : o.getOrganizationIdentification())
                oi.setAttachment((AttachmentRefOrValue) Hibernate.unproxy(oi.getAttachment()));
        }

        o.setOrganizationParentRelationship((OrganizationParentRelationship)
                Hibernate.unproxy(o.getOrganizationParentRelationship()));
        if(o.getOrganizationParentRelationship() != null)
            o.getOrganizationParentRelationship().setOrganization((OrganizationRef)
                    Hibernate.unproxy(o.getOrganizationParentRelationship().getOrganization()));

        o.setOtherName((List<OtherNameOrganization>) Hibernate.unproxy(o.getOtherName()));
        o.setPartyCharacteristic((List<Characteristic>) Hibernate.unproxy(o.getPartyCharacteristic()));
        o.setRelatedParty((List<RelatedParty>) Hibernate.unproxy(o.getRelatedParty()));

        o.setTaxExemptionCertificate((List<TaxExemptionCertificate>)
                Hibernate.unproxy(o.getTaxExemptionCertificate()));
        if(o.getTaxExemptionCertificate() != null) {
            for(TaxExemptionCertificate tec : o.getTaxExemptionCertificate()) {
                tec.setAttachment((AttachmentRefOrValue) Hibernate.unproxy(tec.getAttachment()));
                tec.setTaxDefinition((List<TaxDefinition>) Hibernate.unproxy(tec.getTaxDefinition()));
            }
        }

        log.info("Organization " + id + " retrieved.");

        return o;
    }
}
