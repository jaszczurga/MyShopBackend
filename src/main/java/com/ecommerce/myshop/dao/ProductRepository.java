package com.ecommerce.myshop.dao;

import com.ecommerce.myshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface ProductRepository extends JpaRepository<Product, Long> {

    //products search by category id
    Page<Product> findByCategoryId(@Param ("id") Long id, Pageable pageable);

    //products search by name containing
    Page<Product> findByProductNameContaining(@Param("name") String name, Pageable pageable);
}
