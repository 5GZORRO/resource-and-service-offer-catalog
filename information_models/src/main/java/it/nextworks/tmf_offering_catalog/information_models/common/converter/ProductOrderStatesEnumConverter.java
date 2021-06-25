package it.nextworks.tmf_offering_catalog.information_models.common.converter;

import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderStatesEnum;

import javax.persistence.AttributeConverter;

public class ProductOrderStatesEnumConverter implements AttributeConverter<ProductOrderStatesEnum, String> {

    @Override
    public String convertToDatabaseColumn(ProductOrderStatesEnum productOrderStatesEnum) {
        if (productOrderStatesEnum == null)
            return null;
        return productOrderStatesEnum.toString();
    }

    @Override
    public ProductOrderStatesEnum convertToEntityAttribute(String productOrderStatesEnum) {
        return ProductOrderStatesEnum.fromValue(productOrderStatesEnum);
    }
}
