package com.toyshop.toyshop.dao;

import com.toyshop.toyshop.model.Brand;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BrandDao extends CrudRepository<Brand,Long>{
    Brand findByName(String name);
}
