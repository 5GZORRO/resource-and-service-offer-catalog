package it.nextworks.tmf_offering_catalogue.converters;

import it.nextworks.tmf_offering_catalogue.information_models.StatusEnum;

import javax.persistence.AttributeConverter;

public class StatusEnumConverter implements AttributeConverter<StatusEnum, String> {

    @Override
    public String convertToDatabaseColumn(StatusEnum statusEnum) {
        return statusEnum.toString();
    }

    @Override
    public StatusEnum convertToEntityAttribute(String statusEnum) {
        return StatusEnum.fromValue(statusEnum);
    }
}
