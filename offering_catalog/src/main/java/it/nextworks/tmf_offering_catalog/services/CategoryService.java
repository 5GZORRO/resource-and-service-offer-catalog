package it.nextworks.tmf_offering_catalog.services;

import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.common.LifecycleStatusEnumEnum;
import it.nextworks.tmf_offering_catalog.information_models.common.TimePeriod;
import it.nextworks.tmf_offering_catalog.information_models.product.*;
import it.nextworks.tmf_offering_catalog.repo.CategoryRepository;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.threeten.bp.Instant;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);

    private static final String protocol = "http://";
    @Value("${server.hostname}")
    private String hostname;
    @Value("${server.port}")
    private String port;
    private static final String path = "/tmf-api/productCatalogManagement/v4/category/";

    @Autowired
    private CategoryRepository categoryRepository;

    public Category create(CategoryCreate categoryCreate) {

        log.info("Received request to create a Category.");

        final String id = UUID.randomUUID().toString();
        Category category = new Category()
                .baseType(categoryCreate.getBaseType())
                .schemaLocation(categoryCreate.getSchemaLocation())
                .type(categoryCreate.getType())
                .description(categoryCreate.getDescription())
                .href(protocol + hostname + ":" + port + path + id)
                .id(id)
                .isRoot(categoryCreate.isIsRoot())
                .lifecycleStatus(categoryCreate.getLifecycleStatus())
                .name(categoryCreate.getName())
                .parentId(categoryCreate.getParentId())
                .productOffering(categoryCreate.getProductOffering())
                .subCategory(categoryCreate.getSubCategory())
                .validFor(categoryCreate.getValidFor())
                .version(categoryCreate.getVersion());

        final OffsetDateTime lastUpdate = categoryCreate.getLastUpdate();
        if(lastUpdate != null)
            category.setLastUpdate(lastUpdate.toString());

        category.setLifecycleStatusEnum(LifecycleStatusEnumEnum.fromValue(categoryCreate.getLifecycleStatus()));

        categoryRepository.save(category);

        log.info("Category created with id " + category.getId() + ".");

        return category;
    }

    public void delete(String id) throws NotExistingEntityException {

        log.info("Received request to delete Category with id " + id + ".");

        Optional<Category> toDelete = categoryRepository.findByCategoryId(id);
        if(!toDelete.isPresent())
            throw new NotExistingEntityException("Category with id " + id + " not found in DB.");

        categoryRepository.delete(toDelete.get());

        log.info("Category " + id + " deleted.");
    }

    public List<Category> list() {

        log.info("Received request to retrieve all Categories.");

        List<Category> categories = categoryRepository.findAll();
        for(Category c : categories) {
            c.setProductOffering((List<ProductOfferingRef>) Hibernate.unproxy(c.getProductOffering()));
            c.setSubCategory((List<CategoryRef>) Hibernate.unproxy(c.getSubCategory()));
        }

        log.info("Categories retrieved.");

        return categories;
    }

    public Category patch(String id, CategoryUpdate categoryUpdate) throws NotExistingEntityException {

        log.info("Received request to patch Category with id " + id + ".");

        Optional<Category> toUpdate = categoryRepository.findByCategoryId(id);
        if(!toUpdate.isPresent())
            throw new NotExistingEntityException("Category with id " + id + " not found in DB.");

        Category category = toUpdate.get();

        final String baseType = categoryUpdate.getBaseType();
        if(baseType != null)
            category.setBaseType(baseType);

        final String schemaLocation = categoryUpdate.getSchemaLocation();
        if(schemaLocation != null)
            category.setSchemaLocation(schemaLocation);

        final String type = categoryUpdate.getType();
        if(type != null)
            category.setType(type);

        final String description = categoryUpdate.getDescription();
        if(description != null)
            category.setDescription(description);

        final Boolean isRoot = categoryUpdate.isIsRoot();
        if(isRoot != null)
            category.setIsRoot(isRoot);

        category.setLastUpdate(OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toString());

        final String lifecycleStatus = categoryUpdate.getLifecycleStatus();
        if(lifecycleStatus != null) {
            category.setLifecycleStatus(lifecycleStatus);
            category.setLifecycleStatusEnum(LifecycleStatusEnumEnum.fromValue(lifecycleStatus));
        }

        final String name = categoryUpdate.getName();
        if(name != null)
            category.setName(name);

        final String parentId = categoryUpdate.getParentId();
        if(parentId != null)
            category.setParentId(parentId);

        final List<ProductOfferingRef> productOffering = categoryUpdate.getProductOffering();
        if(productOffering != null) {
            category.getProductOffering().clear();
            category.getProductOffering().addAll(productOffering);
        }
        else
            category.setProductOffering((List<ProductOfferingRef>) Hibernate.unproxy(category.getProductOffering()));

        final List<CategoryRef> subCategory = categoryUpdate.getSubCategory();
        if(subCategory != null) {
            category.getSubCategory().clear();
            category.getSubCategory().addAll(subCategory);
        }
        else
            category.setSubCategory((List<CategoryRef>) Hibernate.unproxy(category.getSubCategory()));

        final TimePeriod validFor = categoryUpdate.getValidFor();
        if(validFor != null)
            category.setValidFor(validFor);

        final String version = categoryUpdate.getVersion();
        if(version != null)
            category.setVersion(version);

        categoryRepository.save(category);

        log.info("Category " + id + " patched.");

        return category;
    }

    public Category get(String id) throws NotExistingEntityException {

        log.info("Received request to retrieve Category with id " + id + ".");

        Optional<Category> retrieved = categoryRepository.findByCategoryId(id);
        if(!retrieved.isPresent())
            throw new NotExistingEntityException("Category with id " + id + " not found in DB.");

        Category c = retrieved.get();

        c.setProductOffering((List<ProductOfferingRef>) Hibernate.unproxy(c.getProductOffering()));
        c.setSubCategory((List<CategoryRef>) Hibernate.unproxy(c.getSubCategory()));

        log.info("Category " + id + " retrieved.");

        return c;
    }
}
