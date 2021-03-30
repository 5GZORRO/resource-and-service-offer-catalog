package it.nextworks.tmf_offering_catalog.information_models.common.converter;

import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingStatesEnum;

import javax.persistence.AttributeConverter;

public class ProductOfferingStatesEnumConverter implements AttributeConverter<ProductOfferingStatesEnum, String> {

    @Override
    public String convertToDatabaseColumn(ProductOfferingStatesEnum productOfferingStatesEnum) {
        return productOfferingStatesEnum.toString();
    }

    @Override
    public ProductOfferingStatesEnum convertToEntityAttribute(String productOfferingStatesEnum) {
        return ProductOfferingStatesEnum.fromValue(productOfferingStatesEnum);
    }
}
