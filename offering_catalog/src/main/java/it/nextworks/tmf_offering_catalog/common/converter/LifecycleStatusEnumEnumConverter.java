package it.nextworks.tmf_offering_catalog.common.converter;

import it.nextworks.tmf_offering_catalog.information_models.LifecycleStatusEnumEnum;

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
