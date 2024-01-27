package com.ecommerce.myshop.dao;

import com.ecommerce.myshop.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource (collectionResourceRel = "productCategories", path = "productCategories")
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
