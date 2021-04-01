package it.nextworks.tmf_offering_catalog.information_models.common.converter;

import it.nextworks.tmf_offering_catalog.information_models.common.LifecycleStatusEnumEnum;

import javax.persistence.AttributeConverter;

public class LifecycleStatusEnumEnumConverter implements AttributeConverter<LifecycleStatusEnumEnum, String> {

    @Override
    public String convertToDatabaseColumn(LifecycleStatusEnumEnum lifecycleStatusEnumEnum) {
        if(lifecycleStatusEnumEnum == null)
            return null;

        return lifecycleStatusEnumEnum.toString();
    }

    @Override
    public LifecycleStatusEnumEnum convertToEntityAttribute(String lifecycleStatusEnumEnum) {
        return LifecycleStatusEnumEnum.fromValue(lifecycleStatusEnumEnum);
    }
}
