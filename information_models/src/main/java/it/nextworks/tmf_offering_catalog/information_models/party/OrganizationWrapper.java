package it.nextworks.tmf_offering_catalog.information_models.party;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OrganizationWrapper {

    @JsonProperty("organization")
    private final Organization organization;

    @JsonProperty("stakeholderDID")
    private final String stakeholderDID;

    @JsonProperty("token")
    private final String token;

    @JsonCreator
    public OrganizationWrapper(@JsonProperty("organization") Organization organization,
                               @JsonProperty("stakeholderDID") String stakeholderDID,
                               @JsonProperty("token") String token) {

        this.organization   = organization;
        this.stakeholderDID = stakeholderDID;
        this.token          = token;
    }

    public Organization getOrganization() { return organization; }

    public String getStakeholderDID() { return stakeholderDID; }

    public String getToken() { return token; }
}
