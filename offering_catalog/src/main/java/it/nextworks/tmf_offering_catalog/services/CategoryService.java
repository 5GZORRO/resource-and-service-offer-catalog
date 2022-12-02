package it.nextworks.tmf_offering_catalog.services;

import it.nextworks.tmf_offering_catalog.common.exception.CategoryAlreadyExistingException;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.common.LifecycleStatusEnumEnum;
import it.nextworks.tmf_offering_catalog.information_models.product.*;
import it.nextworks.tmf_offering_catalog.repo.CategoryRepository;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.threeten.bp.Instant;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);

    private static final String protocol = "http://";
    @Value("${server.hostname}")
    private String hostname;
    @Value("${server.port}")
    private String port;
    private static final String path = "/tmf-api/productCatalogManagement/v4/category/";

    @Value("${ingress:}")
    private String ingres;

    @Autowired
    private CategoryRepository categoryRepository;

    public Category create(CategoryCreate categoryCreate) throws CategoryAlreadyExistingException {

        log.info("Received request to create a Category.");

        String name = categoryCreate.getName();
        if(categoryRepository.findByName(name).isPresent())
            throw new CategoryAlreadyExistingException("Category with name " + name + " already exists.");

        final String id = UUID.randomUUID().toString();
        Category category = new Category()
                .baseType(categoryCreate.getBaseType())
                .schemaLocation(categoryCreate.getSchemaLocation())
                .type(categoryCreate.getType())
                .description(categoryCreate.getDescription())
                .href(StringUtils.hasText(ingres) ? ingres : (protocol + hostname + ":" + port) + path + id)
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
        else
            category.setLastUpdate(OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toString());

        category.setLifecycleStatusEnum(LifecycleStatusEnumEnum.fromValue(categoryCreate.getLifecycleStatus()));

        categoryRepository.save(category);

        log.info("Category created with id " + id + ".");

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

    @Transactional
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

    public Category patch(String id, CategoryUpdate categoryUpdate, String lastUpdate) throws NotExistingEntityException {

        log.info("Received request to patch Category with id " + id + ".");

        Optional<Category> toUpdate = categoryRepository.findByCategoryId(id);
        if(!toUpdate.isPresent())
            throw new NotExistingEntityException("Category with id " + id + " not found in DB.");

        Category category = toUpdate.get();

        category.setBaseType(categoryUpdate.getBaseType());
        category.setSchemaLocation(categoryUpdate.getSchemaLocation());
        category.setType(categoryUpdate.getType());
        category.setDescription(categoryUpdate.getDescription());
        category.setIsRoot(categoryUpdate.isIsRoot());
        category.setLastUpdate(lastUpdate);

        final String lifecycleStatus = categoryUpdate.getLifecycleStatus();
        category.setLifecycleStatus(lifecycleStatus);
        if(lifecycleStatus == null)
            category.setLifecycleStatusEnum(null);
        else
            category.setLifecycleStatusEnum(LifecycleStatusEnumEnum.fromValue(lifecycleStatus));

        category.setName(categoryUpdate.getName());
        category.setParentId(categoryUpdate.getParentId());

        final List<ProductOfferingRef> productOffering = categoryUpdate.getProductOffering();
        if(category.getProductOffering() == null)
            category.setProductOffering(productOffering);
        else if(productOffering != null) {
            category.getProductOffering().clear();
            category.getProductOffering().addAll(productOffering);
        }
        else
            category.getProductOffering().clear();

        final List<CategoryRef> subCategory = categoryUpdate.getSubCategory();
        if(category.getSubCategory() == null)
            category.setSubCategory(subCategory);
        else if(subCategory != null) {
            category.getSubCategory().clear();
            category.getSubCategory().addAll(subCategory);
        }
        else
            category.getSubCategory().clear();

        category.setValidFor(categoryUpdate.getValidFor());
        category.setVersion(categoryUpdate.getVersion());

        categoryRepository.save(category);

        log.info("Category " + id + " patched.");

        return category;
    }

    @Transactional
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

    public Category save(Category c) { return categoryRepository.save(c); }
}
