package com.ecommerce.myshop.dao;

import com.ecommerce.myshop.entity.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageModel, Long>{
    Optional<ImageModel> findByName(String name);
}
