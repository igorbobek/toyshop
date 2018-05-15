package com.toyshop.toyshop.dao;

import com.toyshop.toyshop.model.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CountryDao extends CrudRepository<Country,Long>{
    Country findByName(String name);
}
