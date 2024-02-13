package com.ecommerce.myshop.config;

import com.ecommerce.myshop.entity.Product;
import com.ecommerce.myshop.entity.ProductCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config , CorsRegistry cors) {
        HttpMethod[] theUnsupportedActions = {
                HttpMethod.PUT,
                HttpMethod.POST,
                HttpMethod.DELETE,
                HttpMethod.PATCH
        };

        disableHttpMethods( Product.class,config, theUnsupportedActions);
        disableHttpMethods( ProductCategory.class,config, theUnsupportedActions);
        // call an internal helper method
        exposeIds(config);

        cors.addMapping(config.getBasePath()+"/**").allowedOrigins("*");


    }

    private static void disableHttpMethods(Class theClass,RepositoryRestConfiguration config , HttpMethod[] theUnsupportedActions) {
        config
                .getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metadata , httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metadata , httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }

    private void exposeIds(RepositoryRestConfiguration config) {

        // expose entity ids

        // - get a list of all entity classes from the entity manager
        // it is a set of all entity classes (Product, ProductCategory, etc.)
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        // - create an array of the entity types
        //it is a list of entity classes (Product, ProductCategory, etc.)
        List<Class> entityClassTypes = new ArrayList<>();

        // - expose the entity ids for the array of entity/domain types
        for (EntityType tempEntityType : entities) {
            entityClassTypes.add(tempEntityType.getJavaType());
        }

        // - expose the entity ids for the array of entity/domain types
        Class[] domainTypes = entityClassTypes.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}
