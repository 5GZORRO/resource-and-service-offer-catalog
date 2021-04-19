package it.nextworks.tmf_offering_catalog.information_models.party;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OrganizationCreateWrapper {

    @JsonProperty("organizationCreate")
    private final OrganizationCreate organizationCreate;

    @JsonProperty("stakeholderDID")
    private final String stakeholderDID;

    @JsonProperty("token")
    private final String token;

    @JsonCreator
    public OrganizationCreateWrapper(@JsonProperty("organizationCreate") OrganizationCreate organizationCreate,
                                     @JsonProperty("stakeholderDID") String stakeholderDID,
                                     @JsonProperty("token") String token) {

        this.organizationCreate = organizationCreate;
        this.stakeholderDID     = stakeholderDID;
        this.token              = token;
    }

    public OrganizationCreate getOrganizationCreate() { return organizationCreate; }

    public String getStakeholderDID() { return stakeholderDID; }

    public String getToken() { return token; }
}
