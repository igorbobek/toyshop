package com.toyshop.toyshop.dao;

import com.toyshop.toyshop.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CategoryDao extends CrudRepository<Category, Long> {
    Category findByName(String name);
}
