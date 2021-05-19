package it.nextworks.tmf_offering_catalog.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

public class Filter {

    @JsonProperty("category")
    @ApiModelProperty("Category of the Product Offerings to be provided in response.")
    private String category;

    @JsonProperty("minPrice")
    @ApiModelProperty(value = "Minimum price of the Product Offerings to be provided in response.", example = "0.00")
    private Float minPrice;

    @JsonProperty("maxPrice")
    @ApiModelProperty(value = "Maximum price of the Product Offerings to be provided in response.", example = "0.00")
    private Float maxPrice;

    @JsonProperty("currency")
    @ApiModelProperty("Currency of the Product Offerings to be provided in response.")
    private String currency;

    @JsonProperty("stakeholder")
    @ApiModelProperty("Stakeholder of the Product Offerings to be provided in response.")
    private String stakeholder;

    @JsonProperty("location")
    @ApiModelProperty("Location of the Product Offerings to be provided in response.")
    private String location;

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public Float getMinPrice() { return minPrice; }

    public void setMinPrice(Float minPrice) { this.minPrice = minPrice; }

    public Float getMaxPrice() { return maxPrice; }

    public void setMaxPrice(Float maxPrice) { this.maxPrice = maxPrice; }

    public String getCurrency() { return currency; }

    public void setCurrency(String currency) { this.currency = currency; }

    public String getStakeholder() { return stakeholder; }

    public void setStakeholder(String stakeholder) { this.stakeholder = stakeholder; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }
}
