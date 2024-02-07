package com.ecommerce.myshop.dao;

import com.ecommerce.myshop.entity.ImageModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageModel, Long>{
    Optional<ImageModel> findByName(String name);

    Page<ImageModel> findByProductId(@Param ("id") Long id, Pageable pageable);
}
