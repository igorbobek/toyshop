package com.toyshop.toyshop.dao;

import com.toyshop.toyshop.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
public interface ProductDao extends CrudRepository<Product, Long> {
    Set<Product> findByCategoryId(Long id);
}
