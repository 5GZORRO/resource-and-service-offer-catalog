package it.nextworks.tmf_offering_catalog.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.nextworks.tmf_offering_catalog.common.exception.CategoryAlreadyExistingException;
import it.nextworks.tmf_offering_catalog.common.exception.StakeholderAlreadyRegisteredException;
import it.nextworks.tmf_offering_catalog.information_models.party.OrganizationCreate;
import it.nextworks.tmf_offering_catalog.information_models.party.OrganizationCreateWrapper;
import it.nextworks.tmf_offering_catalog.information_models.product.CategoryCreate;
import it.nextworks.tmf_offering_catalog.information_models.stakeholder.*;
import it.nextworks.tmf_offering_catalog.services.CategoryService;
import it.nextworks.tmf_offering_catalog.services.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BasePathController {

    private final static Logger log = LoggerFactory.getLogger(BasePathController.class);

    private OrganizationService organizationService;

    private CategoryService categoryService;

    @Autowired
    public BasePathController(OrganizationService organizationService, CategoryService categoryService) {
        this.organizationService = organizationService;
        this.categoryService = categoryService;
    }

    private ResponseEntity<ErrMsg> badRequestResponse(String msg) {
        log.error("Web-Server: " + msg);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg(msg));
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "redirect:swagger-ui/";
    }

    @ApiOperation(value = "RSOC admin onboarding handler", nickname = "adminOnboardHandler",
            notes = "Endpoint used by ID&P to trigger the admin onboarding")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrMsg.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrMsg.class)
    })
    @PostMapping(value = "/adminOnboardHandler", produces = "application/json;charset=utf-8")
    public
    ResponseEntity<?> adminOnboardHandler(@ApiParam(value = "Stakeholder Status", required = true)
                                          @Valid @RequestBody Stakeholder stakeholder) {

        log.info("Web-Server: Received request to onboard Stakeholder.");

        if(stakeholder.getId_token() == null)
            return badRequestResponse("Invalid Stakeholder Status, empty ID Token.");

        StakeholderClaim stakeholderClaim = stakeholder.getStakeholderClaim();
        if(stakeholderClaim == null)
            return badRequestResponse("Invalid Stakeholder Status, empty Stakeholder Claim.");

        if(stakeholderClaim.getStakeholderDID() == null)
            return badRequestResponse("Invalid Stakeholder Status, empty Stakeholder DID.");

        List<StakeholderRole> stakeholderRoles = stakeholderClaim.getStakeholderRoles();
        if(stakeholderRoles == null)
            return badRequestResponse("Invalid Stakeholder Status, empty Stakeholder Role list.");

        StakeholderProfile stakeholderProfile = stakeholderClaim.getStakeholderProfile();
        if(stakeholderProfile == null)
            return badRequestResponse("Invalid Stakeholder Status, empty Stakeholder Profile.");

        if(stakeholderProfile.getName() == null)
            return badRequestResponse("Invalid Stakeholder Status, empty Stakeholder Name.");

        String name = stakeholderProfile.getName();
        String stakeholderDID = stakeholderClaim.getStakeholderDID();
        String id_token = stakeholder.getId_token();

        try {
            organizationService.create(new OrganizationCreateWrapper(new OrganizationCreate().name(name),
                    stakeholderDID, id_token));
        } catch (StakeholderAlreadyRegisteredException e) {
            return badRequestResponse("Stakeholder already registered.");
        }

        for(StakeholderRole stakeholderRole : stakeholderRoles) {
            List<Asset> assets = stakeholderRole.getAssets();

            if(assets == null)
                continue;

            for(Asset asset : assets) {
                try {
                    categoryService.create(new CategoryCreate().name(asset.toString()));
                } catch (CategoryAlreadyExistingException ignored) {}
            }
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
