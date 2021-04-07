package it.nextworks.tmf_offering_catalog.information_models.common.converter;

import it.nextworks.tmf_offering_catalog.information_models.party.PartyStatusEnum;

import javax.persistence.AttributeConverter;

public class PartyStatusEnumConverter implements AttributeConverter<PartyStatusEnum, String> {

    @Override
    public String convertToDatabaseColumn(PartyStatusEnum partyStatusEnum) {
        return partyStatusEnum.toString();
    }

    @Override
    public PartyStatusEnum convertToEntityAttribute(String partyStatusEnum) {
        return PartyStatusEnum.fromValue(partyStatusEnum);
    }
}
