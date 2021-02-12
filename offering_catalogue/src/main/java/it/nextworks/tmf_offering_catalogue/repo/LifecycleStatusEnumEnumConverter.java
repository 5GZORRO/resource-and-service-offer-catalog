package it.nextworks.tmf_offering_catalogue.repo;

import it.nextworks.tmf_offering_catalogue.information_models.LifecycleStatusEnumEnum;

import javax.persistence.AttributeConverter;

public class LifecycleStatusEnumEnumConverter implements AttributeConverter<LifecycleStatusEnumEnum, String> {

    @Override
    public String convertToDatabaseColumn(LifecycleStatusEnumEnum lifecycleStatusEnumEnum) {
        return lifecycleStatusEnumEnum.toString();
    }

    @Override
    public LifecycleStatusEnumEnum convertToEntityAttribute(String lifecycleStatusEnumEnum) {
        return LifecycleStatusEnumEnum.fromValue(lifecycleStatusEnumEnum);
    }
}
